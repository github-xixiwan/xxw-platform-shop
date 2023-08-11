package com.xxw.shop.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.order.vo.OrderItemVO;
import com.xxw.shop.entity.OrderItem;
import com.xxw.shop.mapper.OrderItemMapper;
import com.xxw.shop.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.xxw.shop.entity.table.OrderItemTableDef.ORDER_ITEM;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Override
    public List<OrderItemVO> listOrderItemsByOrderId(Long orderId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(ORDER_ITEM.ORDER_ID.eq(orderId));
        return this.listAs(queryWrapper, OrderItemVO.class);
    }

    @Override
    public List<String> getSpuNameListByOrderIds(long[] orderIdList) {
        return mapper.getSpuNameListByOrderIds(orderIdList);
    }

    @Override
    public long countByOrderId(Long orderId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(ORDER_ITEM.ORDER_ID.eq(orderId));
        return this.count(queryWrapper);
    }
}
