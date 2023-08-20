package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.goods.dto.SkuStockLockDTO;
import com.xxw.shop.api.goods.feign.ShopCartFeignClient;
import com.xxw.shop.api.goods.feign.SkuStockLockFeignClient;
import com.xxw.shop.api.order.vo.*;
import com.xxw.shop.module.common.bo.EsOrderBO;
import com.xxw.shop.module.common.vo.ShopCartItemVO;
import com.xxw.shop.api.order.constant.OrderStatus;
import com.xxw.shop.constant.DeliveryType;
import com.xxw.shop.constant.OrderBusinessError;
import com.xxw.shop.dto.DeliveryOrderDTO;
import com.xxw.shop.entity.OrderAddr;
import com.xxw.shop.entity.OrderInfo;
import com.xxw.shop.entity.OrderItem;
import com.xxw.shop.mapper.OrderInfoMapper;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.OrderAddrService;
import com.xxw.shop.service.OrderInfoService;
import com.xxw.shop.service.OrderItemService;
import com.xxw.shop.stream.produce.RocketmqSend;
import com.xxw.shop.vo.*;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.xxw.shop.entity.table.OrderInfoTableDef.ORDER_INFO;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private RocketmqSend rocketmqSend;

    @Resource
    private OrderItemService orderItemService;

    @Resource
    private OrderAddrService orderAddrService;

    @Resource
    private ShopCartFeignClient shopCartFeignClient;

    @Resource
    private SkuStockLockFeignClient skuStockLockFeignClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> submit(Long userId, ShopCartOrderMergerVO mergerOrder) {
        List<OrderInfoCompleteVO> orders = saveOrder(userId, mergerOrder);
        List<Long> orderIds = new ArrayList<>();
        List<SkuStockLockDTO> skuStockLocks = new ArrayList<>();
        for (OrderInfoCompleteVO vo : orders) {
            orderIds.add(vo.getOrderId());
            List<OrderItemVO> orderItems = vo.getOrderItems();
            for (OrderItemVO orderItem : orderItems) {
                skuStockLocks.add(new SkuStockLockDTO(orderItem.getSpuId(), orderItem.getSkuId(),
                        orderItem.getOrderId(), orderItem.getCount()));
            }
        }
        // 锁定库存
        ServerResponseEntity<Void> lockStockResponse = skuStockLockFeignClient.lock(skuStockLocks);
        // 锁定不成，抛异常，让回滚订单
        if (!lockStockResponse.isSuccess()) {
            throw new BusinessException(lockStockResponse.getMessage());
        }
        // 发送消息，如果三十分钟后没有支付，则取消订单
        boolean r = rocketmqSend.orderCancel(orderIds);
        if (!r) {
            // 消息发不出去就抛异常，发的出去无所谓
            throw new BusinessException(SystemErrorEnumError.EXCEPTION);
        }
        return orderIds;
    }


    @Override
    public List<OrderStatusVO> getOrdersStatus(List<Long> orderIds) {
        List<OrderStatusVO> orderStatusList = mapper.getOrdersStatus(orderIds);
        for (Long orderId : orderIds) {
            boolean hasOrderId = false;
            for (OrderStatusVO vo : orderStatusList) {
                if (Objects.equals(vo.getOrderId(), orderId)) {
                    hasOrderId = true;
                }
            }
            if (!hasOrderId) {
                OrderStatusVO vo = new OrderStatusVO();
                vo.setOrderId(orderId);
                orderStatusList.add(vo);
            }
        }
        return orderStatusList;
    }

    @Override
    public OrderAmountVO getOrdersActualAmount(List<Long> orderIds) {
        return mapper.getOrdersActualAmount(orderIds);
    }

    @Override
    public void updateByToPaySuccess(List<Long> orderIds) {
        mapper.updateByToPaySuccess(orderIds);
    }

    @Override
    public List<OrderSimpleAmountInfoVO> getOrdersSimpleAmountInfo(List<Long> orderIds) {
        return mapper.getOrdersSimpleAmountInfo(orderIds);
    }

    /**
     * 取消订单和mq日志要同时落地，所以用分布式事务
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrderAndGetCancelOrderIds(List<Long> orderIds) {
        List<OrderStatusVO> ordersStatus = mapper.getOrdersStatus(orderIds);
        List<Long> cancelOrderIds = new ArrayList<>();
        for (OrderStatusVO vo : ordersStatus) {
            if (vo.getStatus() == null || !Objects.equals(vo.getStatus(), OrderStatus.CLOSE.value())) {
                cancelOrderIds.add(vo.getOrderId());
            }
        }
        if (CollectionUtil.isEmpty(cancelOrderIds)) {
            return;
        }
        mapper.cancelOrders(cancelOrderIds);
        // 解锁库存状态
        boolean r = rocketmqSend.stockUnlock(orderIds);
        if (!r) {
            // 消息发不出去就抛异常，发的出去无所谓
            throw new BusinessException(SystemErrorEnumError.EXCEPTION);
        }
    }

    @Override
    public OrderInfoCompleteVO getOrderByOrderIdAndUserId(Long orderId, Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(ORDER_INFO.ORDER_ID.eq(orderId));
        queryWrapper.and(ORDER_INFO.USER_ID.eq(userId));
        OrderInfoCompleteVO vo = this.getOneAs(queryWrapper, OrderInfoCompleteVO.class);
        if (vo == null) {
            // 订单不存在
            throw new BusinessException(SystemErrorEnumError.ORDER_NOT_EXIST);
        }
        return vo;
    }

    @Override
    public OrderInfoCompleteVO getOrderByOrderId(Long orderId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(ORDER_INFO.ORDER_ID.eq(orderId));
        queryWrapper.and(ORDER_INFO.SHOP_ID.eq(AuthUserContext.get().getTenantId()));
        OrderInfoCompleteVO vo = this.getOneAs(queryWrapper, OrderInfoCompleteVO.class);
        if (vo == null) {
            // 订单不存在
            throw new BusinessException(SystemErrorEnumError.ORDER_NOT_EXIST);
        }
        return vo;
    }

    /**
     * 确认收货订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int receiptOrder(Long orderId) {
        return mapper.receiptOrder(orderId);
    }


    @Override
    public void deleteOrder(Long orderId) {
        mapper.deleteOrder(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional(rollbackFor = Exception.class)
    public void delivery(DeliveryOrderDTO deliveryOrderParam) {
        //修改为发货状态
        LocalDateTime date = LocalDateTime.now();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(deliveryOrderParam.getOrderId());
        orderInfo.setStatus(OrderStatus.CONSIGNMENT.value());
        orderInfo.setUpdateTime(date);
        orderInfo.setDeliveryTime(date);
        //无需物流
        orderInfo.setDeliveryType(DeliveryType.NOT_DELIVERY.value());
        this.updateById(orderInfo);

    }

    @Override
    public SubmitOrderPayAmountInfoVO getSubmitOrderPayAmountInfo(long[] orderIdList) {
        return mapper.getSubmitOrderPayAmountInfo(orderIdList);
    }

    @Override
    public EsOrderBO getEsOrder(Long orderId) {
        return mapper.getEsOrder(orderId);
    }


    public List<OrderInfoCompleteVO> saveOrder(Long userId, ShopCartOrderMergerVO mergerOrder) {
        OrderAddr orderAddr = mapperFacade.map(mergerOrder.getUserAddr(), OrderAddr.class);
        // 地址信息
        if (Objects.isNull(orderAddr)) {
            // 请填写收货地址
            throw new BusinessException(OrderBusinessError.ORDER_00001);
        }
        // 保存收货地址
        orderAddrService.save(orderAddr);
        // 订单商品参数
        List<ShopCartOrderVO> shopCartOrders = mergerOrder.getShopCartOrders();
        List<OrderInfoCompleteVO> orders = new ArrayList<>();
        List<OrderInfo> orderInfos = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        List<Long> shopCartItemIds = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(shopCartOrders)) {
            // 每个店铺生成一个订单
            for (ShopCartOrderVO shopCartOrderDto : shopCartOrders) {
                OrderInfo orderInfo = getOrder(userId, mergerOrder.getDvyType(), shopCartOrderDto);
                for (ShopCartItemVO shopCartItemVO : shopCartOrderDto.getShopCartItemVO()) {
                    OrderItem orderItem = getOrderItem(orderInfo, shopCartItemVO);
                    orderItems.add(orderItem);
                    shopCartItemIds.add(shopCartItemVO.getCartItemId());
                }
                orderInfo.setOrderAddrId(orderAddr.getOrderAddrId());
                orderInfos.add(orderInfo);

                OrderInfoCompleteVO vo = mapperFacade.map(orderInfo, OrderInfoCompleteVO.class);
                vo.setOrderItems(mapperFacade.mapAsList(orderItems, OrderItemVO.class));
                orders.add(vo);
            }
        }
        this.saveBatchSelective(orderInfos);
        orderItemService.saveBatchSelective(orderItems);
        // 清空购物车
        shopCartFeignClient.deleteItem(shopCartItemIds);
        return orders;
    }

    private OrderItem getOrderItem(OrderInfo orderInfo, ShopCartItemVO shopCartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderInfo.getOrderId());
        orderItem.setShopId(shopCartItem.getShopId());
        orderItem.setSkuId(shopCartItem.getSkuId());
        orderItem.setSpuId(shopCartItem.getSpuId());
        orderItem.setSkuName(shopCartItem.getSkuName());
        orderItem.setCount(shopCartItem.getCount());
        orderItem.setSpuName(shopCartItem.getSpuName());
        orderItem.setPic(shopCartItem.getImgUrl());
        orderItem.setPrice(shopCartItem.getSkuPriceFee());
        orderItem.setUserId(orderInfo.getUserId());
        orderItem.setSpuTotalAmount(shopCartItem.getTotalAmount());
        orderItem.setShopCartTime(shopCartItem.getCreateTime());
        // 订单项支付金额
        orderItem.setSpuTotalAmount(shopCartItem.getTotalAmount());
        return orderItem;
    }

    private OrderInfo getOrder(Long userId, Integer dvyType, ShopCartOrderVO shopCartOrderDto) {
        // 订单信息
        OrderInfo orderInfo = new OrderInfo();
        //TODO
        orderInfo.setOrderId(System.currentTimeMillis());
        orderInfo.setShopId(shopCartOrderDto.getShopId());
        orderInfo.setShopName(shopCartOrderDto.getShopName());

        // 用户id
        orderInfo.setUserId(userId);
        // 商品总额
        orderInfo.setTotal(shopCartOrderDto.getTotal());
        orderInfo.setStatus(OrderStatus.UNPAY.value());
        orderInfo.setIsPayed(0);
        orderInfo.setDeleteStatus(0);
        orderInfo.setAllCount(shopCartOrderDto.getTotalCount());
        orderInfo.setDeliveryType(DeliveryType.NOT_DELIVERY.value());
        return orderInfo;
    }

    @Override
    public OrderInfoCompleteVO getOrderAndOrderItemData(Long orderId, Long shopId) {
        return mapper.getOrderAndOrderItemData(orderId, shopId);
    }

    @Override
    public OrderCountVO countNumberOfStatus(Long userId) {
        return mapper.countNumberOfStatus(userId);
    }
}
