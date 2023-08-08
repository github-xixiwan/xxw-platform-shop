package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.SkuStockLock;
import org.apache.ibatis.annotations.Param;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SkuStockLockMapper extends BaseMapper<SkuStockLock> {

    /**
     * 通过订单减少库存
     *
     * @param skuId 商品id
     * @param count 数量
     * @return
     */
    int reduceStockByOrder(@Param("skuId") Long skuId, @Param("count") Integer count);
}
