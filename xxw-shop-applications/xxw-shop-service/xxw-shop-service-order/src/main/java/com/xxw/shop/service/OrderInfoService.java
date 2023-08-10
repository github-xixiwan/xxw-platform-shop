package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.api.order.vo.EsOrderVO;
import com.xxw.shop.api.order.vo.OrderAmountVO;
import com.xxw.shop.api.order.vo.OrderSimpleAmountInfoVO;
import com.xxw.shop.api.order.vo.OrderStatusVO;
import com.xxw.shop.dto.DeliveryOrderDTO;
import com.xxw.shop.entity.OrderInfo;
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

    OrderInfoVO getOrderByOrderIdAndUserId(Long orderId, Long userId);

    OrderInfoVO getOrderByOrderId(Long orderId);

    int receiptOrder(Long orderId);


    void deleteOrder(Long orderId);

    void delivery(DeliveryOrderDTO deliveryOrderParam);

    SubmitOrderPayAmountInfoVO getSubmitOrderPayAmountInfo(long[] orderIdList);

    EsOrderVO getEsOrder(Long orderId);

    List<OrderInfoAndOrderItemVO> saveOrder(Long userId, ShopCartOrderMergerVO mergerOrder);

    OrderInfoAndOrderItemVO getOrderAndOrderItemData(Long orderId, Long shopId);

    OrderCountVO countNumberOfStatus(Long userId);
}
