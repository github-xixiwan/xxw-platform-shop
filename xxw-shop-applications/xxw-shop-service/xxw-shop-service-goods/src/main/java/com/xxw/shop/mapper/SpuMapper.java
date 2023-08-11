package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.api.goods.vo.SpuVO;
import com.xxw.shop.entity.Spu;
import com.xxw.shop.module.common.vo.GoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * 根据spu信息id获取spu信息
     *
     * @param spuId spu信息id
     * @return spu信息
     */
    SpuVO getBySpuId(@Param("spuId") Long spuId);

    /**
     * 更新spu表（canal监听后，会发送更新的消息，更新es中的数据）
     *
     * @param spuIds
     * @param categoryIds
     */
    void updateSpuUpdateTime(@Param("spuIds") List<Long> spuIds, @Param("categoryIds") List<Long> categoryIds);

    /**
     * 根据spuId获取商品信息（搜索）
     *
     * @param spuId
     * @return 商品信息
     */
    GoodsVO loadGoodsVO(@Param("spuId") Long spuId);

    /**
     * 获取 spuId列表
     *
     * @param shopCategoryIds 店铺分类id列表
     * @param categoryIds     平台分类Id列表
     * @param brandId         品牌id
     * @param shopId          店铺id
     * @return spuId列表
     */
    List<Long> getSpuIdsByCondition(@Param("shopCategoryIds") List<Long> shopCategoryIds,
                                    @Param("categoryIds") List<Long> categoryIds, @Param("brandId") Long brandId,
                                    @Param("shopId") Long shopId);

    /**
     * 根据店铺id获取spu列表
     *
     * @param spuIds    商品ids
     * @param spuName   商品名称
     * @param isFailure 是否失效
     * @return 商品列表信息
     */
    List<SpuVO> listBySpuIds(@Param("spuIds") List<Long> spuIds, @Param("spuName") String spuName,
                             @Param("isFailure") Integer isFailure);

    /**
     * 根据分类id列表批量获取商品id列表
     *
     * @param cidList
     * @param type
     * @param shopId
     * @return
     */
    List<Long> listIdsByCidListAndTypeAndShopId(@Param("cidList") List<Long> cidList, @Param("type") int type,
                                                @Param("shopId") Long shopId);

    /**
     * 根据商品id列表与状态批量修改商品状态
     *
     * @param spuIdList
     * @param status
     */
    void batchChangeSpuStatusBySpuIdsAndStatus(@Param("spuIdList") List<Long> spuIdList,
                                               @Param("status") Integer status);

}
