package com.xxw.shop.feign;

import com.xxw.shop.api.business.feign.ShopDetailFeignClient;
import com.xxw.shop.api.business.vo.ShopDetailVO;
import com.xxw.shop.api.goods.feign.GoodsFeignClient;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.common.vo.GoodsVO;
import com.xxw.shop.service.SpuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class GoodsFeignController implements GoodsFeignClient {

    @Resource
    private SpuService spuService;

    @Resource
    private ShopDetailFeignClient shopDetailFeignClient;

    @Override
    public ServerResponseEntity<GoodsVO> loadGoodsVO(Long spuId) {
        GoodsVO esGoodsVO = spuService.loadGoodsVO(spuId);
        // 获取店铺信息
        ServerResponseEntity<ShopDetailVO> shopDetailResponse =
                shopDetailFeignClient.getShopByShopId(esGoodsVO.getShopId());
        ShopDetailVO shopDetail = shopDetailResponse.getData();
        esGoodsVO.setShopName(shopDetail.getShopName());
        esGoodsVO.setShopImg(shopDetail.getShopLogo());
        esGoodsVO.setShopType(shopDetail.getType());
        if (Objects.isNull(esGoodsVO.getSaleNum())) {
            esGoodsVO.setSaleNum(0);
        }
        return ServerResponseEntity.success(esGoodsVO);
    }

    @Override
    public ServerResponseEntity<List<Long>> getSpuIdsByShopCategoryIds(List<Long> shopCategoryIds) {
        return getSpuIdsByCondition(shopCategoryIds, null, null, null);
    }

    @Override
    public ServerResponseEntity<List<Long>> getSpuIdsByCategoryIds(List<Long> categoryIds) {
        return getSpuIdsByCondition(null, categoryIds, null, null);
    }

    @Override
    public ServerResponseEntity<List<Long>> getSpuIdsByBrandId(Long brandId) {
        return getSpuIdsByCondition(null, null, brandId, null);
    }

    @Override
    public ServerResponseEntity<List<Long>> getSpuIdsByShopId(Long shopId) {
        return getSpuIdsByCondition(null, null, null, shopId);
    }

    /**
     * 获取spuId列表
     *
     * @param shopCategoryIds 店铺分类id列表
     * @param categoryIds     平台分类Id列表
     * @param brandId         品牌id
     * @param shopId          店铺id
     * @return
     */
    public ServerResponseEntity<List<Long>> getSpuIdsByCondition(List<Long> shopCategoryIds, List<Long> categoryIds,
                                                                 Long brandId, Long shopId) {
        List<Long> spuIds = spuService.getSpuIdsByCondition(shopCategoryIds, categoryIds, brandId, shopId);
        return ServerResponseEntity.success(spuIds);
    }
}
