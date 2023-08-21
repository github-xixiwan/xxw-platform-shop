package com.xxw.shop.api.search.feign;

import com.xxw.shop.api.search.dto.GoodsSearchDTO;
import com.xxw.shop.api.search.vo.EsGoodsSearchVO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.feign.FeignInsideAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
     * 根据店铺，获取商品分页信息
     *
     * @param dto 参数
     * @return
     */
    @PutMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/searchSpu/spuPage")
    ServerResponseEntity<EsPageVO<EsGoodsSearchVO>> spuPage(@RequestBody GoodsSearchDTO dto);

}
