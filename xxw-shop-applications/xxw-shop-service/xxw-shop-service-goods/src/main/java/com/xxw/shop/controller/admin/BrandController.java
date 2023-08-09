package com.xxw.shop.controller.admin;

import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.BrandService;
import com.xxw.shop.api.goods.vo.BrandVO;
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
 * 品牌信息
 */
@RestController("adminBrandController")
@RequestMapping("/admin/brand")
@Tag(name = "admin-品牌信息")
public class BrandController {

    @Resource
    private BrandService brandService;

    @GetMapping("/get_brand_by_category_id")
    @Parameter(name = "categoryId", description = "分类id")
    @Operation(summary = "根据分类，获取品牌列表", description = "根据分类，获取品牌列表")
    public ServerResponseEntity<List<BrandVO>> getBrandByCategoryId(@RequestParam Long categoryId) {
        return ServerResponseEntity.success(brandService.getBrandByCategoryId(categoryId));
    }
}
