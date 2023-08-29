package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.api.order.vo.*;
import com.xxw.shop.dto.DeliveryOrderDTO;
import com.xxw.shop.entity.OrderInfo;
import com.xxw.shop.module.common.bo.EsOrderBO;
import com.xxw.shop.vo.*;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface OrderInfoService extends IService<OrderInfo> {

    List<Long> submit(Long userId, ShopCartOrderMergerVO mergerOrder);


    List<OrderStatusVO> getOrdersStatus(List<Long> orderIds);

    OrderAmountVO getOrdersActualAmount(List<Long> orderIds);

    void updateByToPaySuccess(List<Long> orderIds);

    List<OrderSimpleAmountInfoVO> getOrdersSimpleAmountInfo(List<Long> orderIds);

    void cancelOrderAndGetCancelOrderIds(List<Long> orderIds);

    OrderCompleteVO getOrderByOrderIdAndUserId(Long orderId, Long userId);

    OrderCompleteVO getOrderByOrderId(Long orderId);

    int receiptOrder(Long orderId);


    void deleteOrder(Long orderId);

    void delivery(DeliveryOrderDTO deliveryOrderParam);

    SubmitOrderPayAmountInfoVO getSubmitOrderPayAmountInfo(long[] orderIdList);

    EsOrderBO getEsOrder(Long orderId);

    List<OrderCompleteVO> saveOrder(Long userId, ShopCartOrderMergerVO mergerOrder);

    OrderCompleteVO getOrderAndOrderItemData(Long orderId, Long shopId);

    OrderCountVO countNumberOfStatus(Long userId);
}
