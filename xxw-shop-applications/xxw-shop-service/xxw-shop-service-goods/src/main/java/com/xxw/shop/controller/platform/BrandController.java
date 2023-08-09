package com.xxw.shop.controller.platform;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.constant.GoodsBusinessError;
import com.xxw.shop.dto.BrandDTO;
import com.xxw.shop.dto.BrandQueryDTO;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.BrandService;
import com.xxw.shop.service.CategoryBrandService;
import com.xxw.shop.service.CategoryService;
import com.xxw.shop.api.goods.vo.BrandVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 品牌信息
 */
@RestController("platformBrandController")
@RequestMapping("/platform/brand")
@Tag(name = "platform-品牌信息")
public class BrandController {

    @Resource
    private BrandService brandService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private CategoryBrandService categoryBrandService;

    @GetMapping("/page")
    @Operation(summary = "获取品牌信息列表", description = "分页获取品牌信息列表")
    public ServerResponseEntity<Page<BrandVO>> page(@Valid BrandQueryDTO dto) {
        return ServerResponseEntity.success(brandService.page(dto));
    }

    @GetMapping
    @Operation(summary = "获取品牌信息", description = "根据brandId获取品牌信息")
    public ServerResponseEntity<BrandVO> getByBrandId(@RequestParam Long brandId) {
        BrandVO brand = brandService.getByBrandId(brandId);
        categoryService.getPathNames(brand.getCategories());
        return ServerResponseEntity.success(brand);
    }

    @PostMapping
    @Operation(summary = "保存品牌信息", description = "保存品牌信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody BrandDTO brandDTO) {
        if (CollUtil.isEmpty(brandDTO.getCategoryIds())) {
            throw new BusinessException(GoodsBusinessError.GOODS_00007);
        }
        if (StrUtil.isEmpty(brandDTO.getName())) {
            throw new BusinessException(GoodsBusinessError.GOODS_00008);
        }
        brandService.saveBrand(brandDTO, brandDTO.getCategoryIds());
        brandService.removeCache(brandDTO.getCategoryIds());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新品牌信息", description = "更新品牌信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody BrandDTO brandDTO) {
        if (CollUtil.isEmpty(brandDTO.getCategoryIds())) {
            throw new BusinessException(GoodsBusinessError.GOODS_00007);
        }
        brandService.updateBrand(brandDTO, brandDTO.getCategoryIds());
        // 清楚缓存
        List<Long> categoryIds = categoryBrandService.getCategoryIdBrandId(brandDTO.getBrandId());
        categoryIds.addAll(brandDTO.getCategoryIds());
        brandService.removeCache(categoryIds);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除品牌信息", description = "根据品牌信息id删除品牌信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long brandId) {
        brandService.deleteById(brandId);
        brandService.removeCache(categoryBrandService.getCategoryIdBrandId(brandId));
        return ServerResponseEntity.success();
    }

    @PutMapping(value = "/update_brand_status")
    @Operation(summary = "更新品牌状态（启用或禁用）", description = "更新品牌状态（启用或禁用）")
    public ServerResponseEntity<Void> updateBrandStatus(@RequestBody BrandDTO brandDTO) {
        if (Objects.isNull(brandDTO.getStatus())) {
            throw new BusinessException(GoodsBusinessError.GOODS_00009);
        }
        if (Objects.isNull(brandDTO.getBrandId())) {
            throw new BusinessException(GoodsBusinessError.GOODS_00010);
        }
        brandService.updateBrandStatus(brandDTO);
        return ServerResponseEntity.success();
    }
}
