package com.xxw.shop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.order.feign.OrderFeignClient;
import com.xxw.shop.api.order.vo.OrderAmountVO;
import com.xxw.shop.constant.PayStatus;
import com.xxw.shop.dto.PayInfoDTO;
import com.xxw.shop.entity.PayInfo;
import com.xxw.shop.mapper.PayInfoMapper;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.PayInfoService;
import com.xxw.shop.stream.produce.RocketmqSend;
import com.xxw.shop.vo.PayInfoResultVO;
import com.xxw.shop.vo.PayInfoVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
@Service
public class PayInfoServiceImpl extends ServiceImpl<PayInfoMapper, PayInfo> implements PayInfoService {

    @Resource
    private RocketmqSend rocketmqSend;

    @Resource
    private OrderFeignClient orderFeignClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayInfoVO pay(Long userId, PayInfoDTO dto) {
        //TODO
        Long payId = System.currentTimeMillis();
        List<Long> orderIds = dto.getOrderIds();

        ServerResponseEntity<OrderAmountVO> ordersAmountAndIfNoCancelResponse =
                orderFeignClient.getOrdersAmountAndIfNoCancel(orderIds);
        // 如果订单已经关闭了，此时不能够支付了
        if (!ordersAmountAndIfNoCancelResponse.isSuccess()) {
            throw new BusinessException(ordersAmountAndIfNoCancelResponse.getMessage());
        }
        OrderAmountVO orderAmount = ordersAmountAndIfNoCancelResponse.getData();
        PayInfo payInfo = new PayInfo();
        payInfo.setPayId(payId);
        payInfo.setUserId(userId);
        payInfo.setPayAmount(orderAmount.getPayAmount());
        payInfo.setPayStatus(PayStatus.UNPAY.value());
        payInfo.setSysType(AuthUserContext.get().getSysType());
        payInfo.setVersion(0);
        // 保存多个支付订单号
        payInfo.setOrderIds(StrUtil.join(StrUtil.COMMA, orderIds));
        // 保存预支付信息
        this.save(payInfo);
        PayInfoVO payInfoVO = new PayInfoVO();
        payInfoVO.setBody("商城订单");
        payInfoVO.setPayAmount(orderAmount.getPayAmount());
        payInfoVO.setPayId(payId);
        return payInfoVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paySuccess(PayInfoResultVO payInfoResult, List<Long> orderIds) {
        // 标记为支付成功状态
        PayInfo payInfo = new PayInfo();
        payInfo.setPayId(payInfoResult.getPayId());
        payInfo.setBizPayNo(payInfoResult.getBizPayNo());
        payInfo.setCallbackContent(payInfoResult.getCallbackContent());
        payInfo.setCallbackTime(LocalDateTime.now());
        payInfo.setPayStatus(PayStatus.PAYED.value());
        this.updateById(payInfo);
        // 发送消息，订单支付成功
        boolean r = rocketmqSend.orderNotify(orderIds);
        if (!r) {
            // 消息发不出去就抛异常，因为订单回调会有多次，几乎不可能每次都无法发送出去，发的出去无所谓因为接口是幂等的
            throw new BusinessException(SystemErrorEnumError.EXCEPTION);
        }
    }

    @Override
    public Integer getPayStatusByOrderIds(String orderIds) {
        return mapper.getPayStatusByOrderIds(orderIds);
    }

    @Override
    public Integer isPay(String orderIds, Long userId) {
        return mapper.isPay(orderIds, userId);
    }
}
