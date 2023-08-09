package com.xxw.shop.controller.consumer;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.dto.BrandQueryDTO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.BrandService;
import com.xxw.shop.api.goods.vo.BrandVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 品牌信息
 */
@RestController("consumerBrandController")
@RequestMapping("/ua/brand")
@Tag(name = "app-品牌信息")
public class BrandController {

    @Resource
    private BrandService brandService;

    @GetMapping("/page")
    @Operation(summary = "获取品牌信息列表", description = "分页获取品牌信息列表")
    public ServerResponseEntity<Page<BrandVO>> page(@Valid BrandQueryDTO dto) {
        return ServerResponseEntity.success(brandService.page(dto));
    }

    @GetMapping("/top_brand_list")
    @Operation(summary = "置顶品牌列表", description = "置顶品牌列表")
    public ServerResponseEntity<List<BrandVO>> topBrandList() {
        List<BrandVO> brandPage = brandService.topBrandList();
        return ServerResponseEntity.success(brandPage);
    }

    @GetMapping("/list_by_category")
    @Operation(summary = "分类-推荐品牌信息列表", description = "分类-推荐品牌信息列表")
    public ServerResponseEntity<List<BrandVO>> getTopBrandList(Long categoryId) {
        List<BrandVO> brandPage = brandService.listByCategory(categoryId);
        return ServerResponseEntity.success(brandPage);
    }
}
