package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.SpuAttrValue;
import com.xxw.shop.api.goods.vo.SpuAttrValueVO;
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
     * 根据spuId获取商品属性列表
     *
     * @param spuId
     * @return
     */
    List<SpuAttrValueVO> getSpuAttrsBySpuId(@Param("spuId") Long spuId);
}
