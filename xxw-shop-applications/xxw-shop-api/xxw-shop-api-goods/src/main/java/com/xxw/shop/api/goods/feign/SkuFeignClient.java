package com.xxw.shop.api.goods.feign;

import com.xxw.shop.api.goods.vo.SkuVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.feign.FeignInsideAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "shop-goods", contextId = "skuFeign")
public interface SkuFeignClient {

    /**
     * 通过skuId获取sku信息
     *
     * @param skuId skuId
     * @return sku信息
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/sku/getById")
    ServerResponseEntity<SkuVO> getById(@RequestParam("skuId") Long skuId);

}
