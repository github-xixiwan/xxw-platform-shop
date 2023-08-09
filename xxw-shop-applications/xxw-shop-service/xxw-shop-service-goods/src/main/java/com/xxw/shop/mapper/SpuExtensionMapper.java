package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.SpuExtension;
import com.xxw.shop.vo.SkuStockLockVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SpuExtensionMapper extends BaseMapper<SpuExtension> {

    /**
     * 更新库存
     *
     * @param spuId 商品id
     * @param count 商品数量
     */
    void updateStock(@Param("spuId") Long spuId, @Param("count") Long count);

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
