package com.xxw.shop.controller.consumer;

import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.CategoryService;
import com.xxw.shop.api.goods.vo.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类信息
 */
@RestController("consumerCategoryController")
@RequestMapping("/ua/category")
@Tag(name = "app-分类信息")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/category_list")
    @Operation(summary = "获取指定分类下的分类列表（顶级分类的parentId为0,默认为一级分类）", description = "获取指定分类下的分类列表")
    @Parameters({
            @Parameter(name = "parentId", description = "分类ID"),
            @Parameter(name = "shopId", description = "店铺id")
    })
    public ServerResponseEntity<List<CategoryVO>> categoryList(@RequestParam(value = "parentId", defaultValue = "0") Long parentId, @RequestParam(value = "shopId", defaultValue = "0") Long shopId) {
        List<CategoryVO> categories = categoryService.categoryList(shopId, parentId);
        return ServerResponseEntity.success(categories);
    }

    @GetMapping("/shop_category_list")
    @Operation(summary = "店铺/平台的全部分类列表接口", description = "店铺/平台分类列表接口")
    @Parameter(name = "shopId", description = "店铺id", required = false)
    public ServerResponseEntity<List<CategoryVO>> shopCategoryList(@RequestParam(value = "shopId",
            defaultValue = "0") Long shopId) {
        List<CategoryVO> categories = categoryService.shopCategoryList(shopId);
        return ServerResponseEntity.success(categories);
    }
}
