package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.SkuStock;
import com.xxw.shop.vo.SkuStockLockVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SkuStockMapper extends BaseMapper<SkuStock> {

    /**
     * 通过订单减少库存
     *
     * @param skuId 商品id
     * @param count 数量
     * @return
     */
    int reduceStockByOrder(@Param("skuId") Long skuId, @Param("count") Integer count);

    /**
     * 根据列表中的库存数量，增加sku的库存
     *
     * @param skuStocks 修改信息
     */
    void updateStock(@Param("skuStocks") List<SkuStock> skuStocks);

    /**
     * 通过订单减少实际库存
     *
     * @param skuWithStocks 库存信息
     */
    void reduceActualStockByOrder(@Param("skuWithStocks") List<SkuStockLockVO> skuWithStocks);

    /**
     * 通过已经取消的订单减少实际库存
     *
     * @param skuWithStocks 库存信息
     */
    void reduceActualStockByCancelOrder(@Param("skuWithStocks") List<SkuStockLockVO> skuWithStocks);
}
