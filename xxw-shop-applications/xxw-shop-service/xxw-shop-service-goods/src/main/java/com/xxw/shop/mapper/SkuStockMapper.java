package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.SkuStock;
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
     * 根据列表中的库存数量，增加sku的库存
     *
     * @param skuStocks 修改信息
     */
    void updateStock(@Param("skuStocks") List<SkuStock> skuStocks);
}
