package com.xxw.shop.controller.consumer;

import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.HotSearchService;
import com.xxw.shop.vo.HotSearchVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 热搜
 */
@RestController("consumerHotSearchController")
@RequestMapping("/ua/app/hot_search")
@Tag(name = "consumer-热搜")
public class HotSearchController {

    @Resource
    private HotSearchService hotSearchService;

    @GetMapping("/list")
    @Operation(summary = "获取热搜列表", description = "获取热搜列表")
    @Parameter(name = "shopId", description = "店铺id")
    public ServerResponseEntity<List<HotSearchVO>> listByShopId(@RequestParam("shopId") Long shopId) {
        List<HotSearchVO> hotSearches = hotSearchService.listByShopId(shopId);
        return ServerResponseEntity.success(hotSearches);
    }
}
