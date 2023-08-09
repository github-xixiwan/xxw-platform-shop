package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.SpuExtension;
import org.apache.ibatis.annotations.Param;

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
}
