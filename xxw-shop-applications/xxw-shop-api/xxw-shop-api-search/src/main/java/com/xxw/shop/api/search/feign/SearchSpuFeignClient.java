package com.xxw.shop.api.search.feign;

import com.xxw.shop.api.search.dto.GoodsSearchDTO;
import com.xxw.shop.api.search.vo.EsGoodsSearchVO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.api.search.vo.EsSpuVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.feign.FeignInsideAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品搜索feign连接
 */
@FeignClient(value = "shop-search", contextId = "searchSpuFeign")
public interface SearchSpuFeignClient {

    /**
     * 商品搜索
     *
     * @param dto 参数
     * @return
     */
    @PutMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/searchSpu/search")
    ServerResponseEntity<EsPageVO<EsGoodsSearchVO>> search(@RequestBody GoodsSearchDTO dto);

    /**
     * 根据spuId列表， 获取spu列表信息
     *
     * @param spuIds 商品id列表
     * @return 商品列表
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/searchSpu/getSpusBySpuIds")
    ServerResponseEntity<List<EsSpuVO>> getSpusBySpuIds(@RequestParam("spuIds") List<Long> spuIds);

    /**
     * 根据店铺，获取商品分页信息
     *
     * @param dto 参数
     * @return
     */
    @PutMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/searchSpu/spuPage")
    ServerResponseEntity<EsPageVO<EsGoodsSearchVO>> spuPage(@RequestBody GoodsSearchDTO dto);

    /**
     * 根据店铺id列表获取每个店铺的spu列表
     *
     * @param shopIds 店铺id列表
     * @param size    每个店铺返回的商品数量
     * @return
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/searchSpu/limitSizeListByShopIds")
    ServerResponseEntity<List<EsSpuVO>> limitSizeListByShopIds(@RequestParam("shopIds") List<Long> shopIds,
                                                               @RequestParam("size") Integer size);

}
