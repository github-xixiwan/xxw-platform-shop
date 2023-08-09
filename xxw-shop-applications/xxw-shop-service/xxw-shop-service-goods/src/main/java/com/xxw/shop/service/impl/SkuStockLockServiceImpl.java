package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.dto.SkuStockLockDTO;
import com.xxw.shop.dto.SkuStockLockQueryDTO;
import com.xxw.shop.entity.SkuStockLock;
import com.xxw.shop.mapper.SkuStockLockMapper;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.SkuStockLockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class SkuStockLockServiceImpl extends ServiceImpl<SkuStockLockMapper, SkuStockLock> implements SkuStockLockService {

    @Override
    public Page<SkuStockLock> page(SkuStockLockQueryDTO dto) {
        return this.page(new Page<>(dto.getPageNumber(), dto.getPageSize()));
    }

    @Override
    public SkuStockLock getSkuStockLockById(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseEntity<Void> lock(List<SkuStockLockDTO> skuStockLocksParam) {
        List<SkuStockLock> skuStockLocks = new ArrayList<>();
        for (SkuStockLockDTO skuStockLockDTO : skuStockLocksParam) {
            SkuStockLock skuStockLock = new SkuStockLock();
            skuStockLock.setCount(skuStockLockDTO.getCount());
            skuStockLock.setOrderId(skuStockLockDTO.getOrderId());
            skuStockLock.setSkuId(skuStockLockDTO.getSkuId());
            skuStockLock.setSpuId(skuStockLockDTO.getSpuId());
            skuStockLock.setStatus(0);
            skuStockLocks.add(skuStockLock);
            // 减sku库存
            int skuStockUpdateIsSuccess = mapper.reduceStockByOrder(skuStockLockDTO.getSkuId(),
                    skuStockLockDTO.getCount());
            if (skuStockUpdateIsSuccess < 1) {
                throw new BusinessException(SystemErrorEnumError.NOT_STOCK, "商品skuId: " + skuStockLockDTO.getSkuId());
            }
            // 减商品库存
            int spuStockUpdateIsSuccess = mapper.reduceStockByOrder(skuStockLockDTO.getSpuId(), skuStockLockDTO.getCount());
            if (spuStockUpdateIsSuccess < 1) {
                throw new BusinessException(SystemErrorEnumError.NOT_STOCK, "商品spuId: " + skuStockLockDTO.getSpuId());
            }
        }
        // 保存库存锁定信息
        this.saveBatch(skuStockLocks);
        List<Long> orderIds = skuStockLocksParam.stream().map(SkuStockLockDTO::getOrderId).collect(Collectors.toList());
        // 一个小时后解锁库存
        SendStatus sendStatus = stockMqTemplate.syncSend(RocketMqConstant.STOCK_UNLOCK_TOPIC, new GenericMessage<>(orderIds), RocketMqConstant.TIMEOUT, RocketMqConstant.CANCEL_ORDER_DELAY_LEVEL + 1).getSendStatus();
        if (!Objects.equals(sendStatus,SendStatus.SEND_OK)) {
            // 消息发不出去就抛异常，发的出去无所谓
            throw new Mall4cloudException(ResponseEnum.EXCEPTION);
        }
        return ServerResponseEntity.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlockStock(List<Long> orderIds) {
        ServerResponseEntity<List<OrderStatusBO>> ordersStatusResponse = orderFeignClient.getOrdersStatus(orderIds);
        if (!ordersStatusResponse.isSuccess()) {
            throw new Mall4cloudException(ordersStatusResponse.getMsg());
        }
        List<OrderStatusBO> orderStatusList = ordersStatusResponse.getData();

        List<Long> needUnLockOrderId = new ArrayList<>();
        for (OrderStatusBO orderStatusBO : orderStatusList) {
            // 该订单没有下单成功，或订单已取消，赶紧解锁库存
            if (orderStatusBO.getStatus() == null || Objects.equals(orderStatusBO.getStatus(), OrderStatus.CLOSE.value())) {
                needUnLockOrderId.add(orderStatusBO.getOrderId());
            }
        }

        if (CollectionUtil.isEmpty(needUnLockOrderId)) {
            return;
        }

        List<SkuWithStockBO> allSkuWithStocks = skuStockLockMapper.listByOrderIds(needUnLockOrderId);
        if (CollectionUtil.isEmpty(allSkuWithStocks)) {
            return;
        }
        List<Long> lockIds = allSkuWithStocks.stream().map(SkuWithStockBO::getId).collect(Collectors.toList());

        // 还原商品库存
        spuExtensionMapper.addStockByOrder(allSkuWithStocks);
        // 还原sku库存
        skuStockMapper.addStockByOrder(allSkuWithStocks);
        // 将锁定状态标记为已解锁
        skuStockLockMapper.unLockByIds(lockIds);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markerStockUse(List<Long> orderIds) {

        List<SkuWithStockBO> skuWithStocks = skuStockLockMapper.listByOrderIds(orderIds);

        //  ==============订单从正常状态变成已支付=============
        if (CollectionUtil.isNotEmpty(skuWithStocks)) {
            // 减少商品实际库存，增加销量
            spuExtensionMapper.reduceActualStockByOrder(skuWithStocks);
            // 减少sku实际库存
            skuStockMapper.reduceActualStockByOrder(skuWithStocks);
        }

        // ================ 由于订单支付回调成功过慢，导致订单由取消变成已支付 ====================

        List<SkuWithStockBO> unLockSkuWithStocks = skuStockLockMapper.listUnLockByOrderIds(orderIds);

        if (CollectionUtil.isNotEmpty(unLockSkuWithStocks)) {
            // 减少商品实际库存，增加销量
            spuExtensionMapper.reduceActualStockByCancelOrder(unLockSkuWithStocks);
            // 减少sku实际库存
            skuStockMapper.reduceActualStockByCancelOrder(unLockSkuWithStocks);
        }
        // 将锁定状态标记为已使用
        skuStockLockMapper.markerStockUse(orderIds);
    }
}
