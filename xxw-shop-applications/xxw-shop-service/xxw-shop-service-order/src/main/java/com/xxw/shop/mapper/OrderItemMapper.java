package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    /**
     * 根据订单id获取商品名称
     *
     * @param orderIdList 订单id
     * @return 商品名称列表
     */
    List<String> getSpuNameListByOrderIds(@Param("orderIdList") long[] orderIdList);
}
