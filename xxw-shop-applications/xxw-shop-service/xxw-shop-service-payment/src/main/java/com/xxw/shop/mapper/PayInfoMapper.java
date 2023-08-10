package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.PayInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface PayInfoMapper extends BaseMapper<PayInfo> {

    /**
     * 根据支付订单号获取订单支付状态
     *
     * @param orderIds 订单号ids
     * @return 支付状态
     */
    Integer getPayStatusByOrderIds(@Param("orderIds") String orderIds);

    /**
     * 查询订单是否已经支付
     *
     * @param orderIds 订单id
     * @param userId   用户id
     * @return 是否已经支付
     */
    Integer isPay(@Param("orderIds") String orderIds, @Param("userId") Long userId);

}
