package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.api.order.vo.OrderItemVO;
import com.xxw.shop.entity.OrderItem;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface OrderItemService extends IService<OrderItem> {

    List<OrderItemVO> listOrderItemsByOrderId(Long orderId);

    List<String> getSpuNameListByOrderIds(long[] orderIdList);

    long countByOrderId(Long orderId);
}
