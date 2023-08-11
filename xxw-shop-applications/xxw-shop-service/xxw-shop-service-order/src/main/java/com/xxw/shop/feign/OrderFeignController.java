package com.xxw.shop.feign;

import cn.hutool.core.collection.CollectionUtil;
import com.xxw.shop.api.order.feign.OrderFeignClient;
import com.xxw.shop.api.order.vo.OrderInfoCompleteVO;
import com.xxw.shop.api.order.vo.OrderAmountVO;
import com.xxw.shop.api.order.vo.OrderSimpleAmountInfoVO;
import com.xxw.shop.api.order.vo.OrderStatusVO;
import com.xxw.shop.api.order.constant.OrderStatus;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.OrderInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class OrderFeignController implements OrderFeignClient {

    @Resource
    private OrderInfoService orderInfoService;

    @Override
    public ServerResponseEntity<OrderAmountVO> getOrdersAmountAndIfNoCancel(List<Long> orderIds) {
        List<OrderStatusVO> orderStatus = orderInfoService.getOrdersStatus(orderIds);
        if (CollectionUtil.isEmpty(orderStatus)) {
            return ServerResponseEntity.fail(SystemErrorEnumError.ORDER_NOT_EXIST);
        }

        for (OrderStatusVO statusVO : orderStatus) {
            // 订单已关闭
            if (statusVO.getStatus() == null || Objects.equals(statusVO.getStatus(), OrderStatus.CLOSE.value())) {
                return ServerResponseEntity.fail(SystemErrorEnumError.ORDER_EXPIRED);
            }
        }

        OrderAmountVO orderAmountVO = orderInfoService.getOrdersActualAmount(orderIds);
        return ServerResponseEntity.success(orderAmountVO);
    }

    @Override
    public ServerResponseEntity<List<OrderStatusVO>> getOrdersStatus(List<Long> orderIds) {
        List<OrderStatusVO> orderStatusList = orderInfoService.getOrdersStatus(orderIds);
        return ServerResponseEntity.success(orderStatusList);
    }

    @Override
    public ServerResponseEntity<List<OrderSimpleAmountInfoVO>> getOrdersSimpleAmountInfo(List<Long> orderIds) {
        return ServerResponseEntity.success(orderInfoService.getOrdersSimpleAmountInfo(orderIds));
    }

    @Override
    public ServerResponseEntity<OrderInfoCompleteVO> getEsOrder(Long orderId) {
        OrderInfoCompleteVO esOrderInfoCompleteVO = orderInfoService.getEsOrder(orderId);
        return ServerResponseEntity.success(esOrderInfoCompleteVO);
    }

    @Override
    public ServerResponseEntity<Void> updateOrderState(List<Long> orderIds) {
        return null;
    }
}
