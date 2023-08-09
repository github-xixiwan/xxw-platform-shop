package com.xxw.shop.api.goods.feign;

import com.xxw.shop.api.goods.vo.CategoryVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.feign.FeignInsideAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "shop-goods", contextId = "categoryFeign")
public interface CategoryFeignClient {

    /**
     * 获取所有一级分类信息
     *
     * @return 一级分类信息
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/category/listByOneLevel")
    ServerResponseEntity<List<CategoryVO>> listByOneLevel();

    /**
     * 根据上级id，获取子分类id列表
     *
     * @param categoryId
     * @return
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/category/listCategoryId")
    ServerResponseEntity<List<Long>> listCategoryId(@RequestParam("categoryId") Long categoryId);
}
