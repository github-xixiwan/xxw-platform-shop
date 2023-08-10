package com.xxw.shop.controller.consumer;

import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.IndexImgService;
import com.xxw.shop.vo.IndexImgVO;
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
 * 轮播图
 */
@RestController("consumerIndexImgController")
@RequestMapping("/ua/index_img")
@Tag(name = "consumer-轮播图")
public class IndexImgController {

    @Resource
    private IndexImgService indexImgService;

    @GetMapping("/list")
    @Operation(summary = "获取轮播图列表", description = "分页获取轮播图列表")
    @Parameter(name = "shopId", description = "店铺id（平台：0）")
    public ServerResponseEntity<List<IndexImgVO>> getList(@RequestParam("shopId") Long shopId) {
        List<IndexImgVO> indexImgPage = indexImgService.getListByShopId(shopId);
        return ServerResponseEntity.success(indexImgPage);
    }
}
