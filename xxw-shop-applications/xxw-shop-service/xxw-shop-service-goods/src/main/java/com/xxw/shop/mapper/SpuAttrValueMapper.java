package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.SpuAttrValue;
import com.xxw.shop.vo.SpuAttrValueVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SpuAttrValueMapper extends BaseMapper<SpuAttrValue> {

    /**
     * 根据属性值id，获取spuId列表
     *
     * @param attrValueIds
     * @return
     */
    List<Long> getShopIdByAttrValueIds(@Param("attrValueIds") List<Long> attrValueIds);

    /**
     * 更新spu表（canal监听后，会发送更新的消息，更新es中的数据）
     *
     * @param spuIds
     * @param categoryIds
     */
    void updateSpuUpdateTime(@Param("spuIds") List<Long> spuIds, @Param("categoryIds") List<Long> categoryIds);

    /**
     * 获取 spuId列表
     *
     * @param shopCategoryIds 店铺分类id列表
     * @param categoryIds     平台分类Id列表
     * @param brandId         品牌id
     * @param shopId          店铺id
     * @return spuId列表
     */
    List<Long> getSpuIdsBySpuUpdateDTO(@Param("shopCategoryIds") List<Long> shopCategoryIds,
                                       @Param("categoryIds") List<Long> categoryIds, @Param("brandId") Long brandId,
                                       @Param("shopId") Long shopId);

    /**
     * 批量更新商品基本属性
     *
     * @param spuAttrValues
     */
    void batchUpdateSpuAttrValue(@Param("spuAttrValues") List<SpuAttrValue> spuAttrValues);

    /**
     * 根据spuId获取商品属性列表
     *
     * @param spuId
     * @return
     */
    List<SpuAttrValueVO> getSpuAttrsBySpuId(@Param("spuId") Long spuId);
}
