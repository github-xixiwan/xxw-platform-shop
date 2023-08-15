package com.xxw.shop.controller.admin;

import com.xxw.shop.api.goods.constant.CategoryLevel;
import com.xxw.shop.constant.GoodsBusinessError;
import com.xxw.shop.dto.CategoryDTO;
import com.xxw.shop.module.common.constant.Constant;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.CategoryService;
import com.xxw.shop.api.goods.vo.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 分类信息
 */
@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@Tag(name = "admin-分类信息")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "获取分类信息", description = "根据categoryId获取分类信息")
    public ServerResponseEntity<CategoryVO> getByCategoryId(@RequestParam Long categoryId) {
        return ServerResponseEntity.success(categoryService.getCategoryId(categoryId));
    }

    @PostMapping
    @Operation(summary = "保存分类信息", description = "保存分类信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody CategoryDTO categoryDTO) {
        if (!Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId()) && categoryDTO.getLevel() > CategoryLevel.SECOND.value()) {
            throw new BusinessException(GoodsBusinessError.GOODS_00014);
        }
        categoryService.saveCategory(categoryDTO);
        categoryService.removeCategoryCache(AuthUserContext.get().getTenantId(), categoryDTO.getParentId());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新分类信息", description = "更新分类信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        categoryService.removeCategoryCache(AuthUserContext.get().getTenantId(), categoryDTO.getParentId());
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除分类信息", description = "根据分类信息id删除分类信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long categoryId) {
        CategoryVO categoryVO = categoryService.getCategoryId(categoryId);
        categoryService.deleteById(categoryId);
        categoryService.removeCategoryCache(AuthUserContext.get().getTenantId(), categoryVO.getParentId());
        return ServerResponseEntity.success();
    }

    @GetMapping("/platform_categories")
    @Operation(summary = "获取平台所有的分类信息", description = "获取所有的分类列表信息")
    public ServerResponseEntity<List<CategoryVO>> platformCategories() {
        return ServerResponseEntity.success(categoryService.list(Constant.PLATFORM_SHOP_ID));
    }

    @GetMapping("/shop_categories")
    @Operation(summary = "获取店铺所有的分类信息", description = "获取店铺所有的分类信息")
    public ServerResponseEntity<List<CategoryVO>> shopCategories() {
        return ServerResponseEntity.success(categoryService.list(AuthUserContext.get().getTenantId()));
    }

    @GetMapping("/get_list_by_parent_id")
    @Operation(summary = "根据上级id，获取分类列表信息", description = "根据上级id，获取分类列表信息")
    @Parameters(value = {@Parameter(name = "parentId", description = "父类id")})
    public ServerResponseEntity<List<CategoryVO>> getListByParentId(@RequestParam(value = "parentId") Long parentId) {
        return ServerResponseEntity.success(categoryService.listByShopIdAndParenId(parentId,
                AuthUserContext.get().getTenantId()));
    }

    @PutMapping(value = "/category_enable_or_disable")
    @Operation(summary = "分类的启用或禁用", description = "分类的启用或禁用")
    public ServerResponseEntity<Boolean> categoryEnableOrDisable(@RequestBody CategoryDTO categoryDTO) {
        boolean isSuccess = categoryService.categoryEnableOrDisable(categoryDTO);
        return ServerResponseEntity.success(isSuccess);
    }
}
