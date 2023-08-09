package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.Sku;
import com.xxw.shop.api.goods.vo.SkuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SkuMapper extends BaseMapper<Sku> {

    /**
     * 根据spuId获取sku信息
     *
     * @param spuId id
     * @return 返回sku信息
     */
    List<SkuVO> listBySpuId(@Param("spuId") Long spuId);

    /**
     * 获取商品详细信息
     *
     * @param spuId
     * @return
     */
    List<SkuVO> listBySpuIdAndExtendInfo(@Param("spuId") Long spuId);

    /**
     * 获取商品的sku列表（仅获取启用状态）
     *
     * @param spuId
     * @return
     */
    List<SkuVO> getSkuBySpuId(@Param("spuId") Long spuId);
}
