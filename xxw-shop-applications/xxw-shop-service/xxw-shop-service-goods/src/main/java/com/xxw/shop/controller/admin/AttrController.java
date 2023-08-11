package com.xxw.shop.controller.admin;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.constant.AttrType;
import com.xxw.shop.constant.GoodsBusinessError;
import com.xxw.shop.constant.SearchType;
import com.xxw.shop.dto.AttrDTO;
import com.xxw.shop.dto.AttrQueryDTO;
import com.xxw.shop.module.common.constant.Constant;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.AttrService;
import com.xxw.shop.vo.AttrCompleteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 属性信息
 */
@RestController("adminAttrController")
@RequestMapping("/admin/attr")
@Tag(name = "admin-属性信息")
public class AttrController {

    @Resource
    private AttrService attrService;

    @GetMapping("/page")
    @Operation(summary = "获取属性信息列表", description = "分页获取属性信息列表")
    public ServerResponseEntity<Page<AttrCompleteVO>> page(@Valid AttrQueryDTO dto) {
        return ServerResponseEntity.success(attrService.page(dto));
    }

    @GetMapping
    @Operation(summary = "获取属性信息", description = "根据attrId获取属性信息")
    public ServerResponseEntity<AttrCompleteVO> getByAttrId(@RequestParam Long attrId) {
        return ServerResponseEntity.success(attrService.getByAttrId(attrId));
    }

    @PostMapping
    @Operation(summary = "保存属性信息", description = "保存属性信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody AttrDTO attrDTO) {
        if (Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId()) && Objects.isNull(attrDTO.getAttrType())) {
            throw new BusinessException(GoodsBusinessError.GOODS_00011);
        }
        checkAttrInfo(attrDTO);
        attrService.saveAttr(attrDTO, attrDTO.getCategoryIds());
        removeCacheAttrUnionCategory(attrDTO.getCategoryIds());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新属性信息", description = "更新属性信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody AttrDTO attrDTO) {
        checkAttrInfo(attrDTO);
        List<Long> categoryIds = null;
        if (Objects.equals(AttrType.BASIC.value(), attrDTO.getAttrType())) {
            categoryIds = attrService.getAttrOfCategoryIdByAttrId(attrDTO.getAttrId());
            categoryIds.addAll(attrDTO.getCategoryIds());
        }
        attrService.updateAttr(attrDTO, attrDTO.getCategoryIds());
        removeCacheAttrUnionCategory(categoryIds);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除属性信息", description = "根据属性信息id删除属性信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long attrId) {
        List<Long> categoryIds = attrService.getAttrOfCategoryIdByAttrId(attrId);
        attrService.deleteById(attrId);
        if (CollUtil.isNotEmpty(categoryIds)) {
            removeCacheAttrUnionCategory(categoryIds);
        }
        return ServerResponseEntity.success();
    }

    @GetMapping("/get_attrs_by_category_id")
    @Operation(summary = "根据分类及属性类别获取属性列表", description = "根据分类及属性类别获取属性列表")
    @Parameter(name = "categoryId", description = "分类id", required = true)
    public ServerResponseEntity<List<AttrCompleteVO>> getAttrsByCategoryId(@RequestParam(value = "categoryId") Long categoryId) {
        return ServerResponseEntity.success(attrService.getAttrsByCategoryIdAndAttrType(categoryId));
    }

    @GetMapping("/get_shop_attrs")
    @Operation(summary = "获取店铺中的销售属性", description = "获取店铺中的销售属性")
    public ServerResponseEntity<List<AttrCompleteVO>> getShopAttrs() {
        return ServerResponseEntity.success(attrService.getShopAttrs(AuthUserContext.get().getTenantId()));
    }

    /**
     * 校验属性数据
     *
     * @param attrDTO
     */
    private void checkAttrInfo(AttrDTO attrDTO) {
        if (!Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId())) {
            attrDTO.setAttrType(AttrType.SALES.value());
        }
        if (Objects.equals(AttrType.SALES.value(), attrDTO.getAttrType())) {
            attrDTO.setSearchType(SearchType.NOT_SEARCH.value());
            return;
        }
        if (CollUtil.isEmpty(attrDTO.getCategoryIds())) {
            throw new BusinessException(GoodsBusinessError.GOODS_00012);
        }
        if (Objects.isNull(attrDTO.getSearchType())) {
            throw new BusinessException(GoodsBusinessError.GOODS_00013);
        }
    }

    /**
     * 删除属性关联的分类缓存
     */
    private void removeCacheAttrUnionCategory(List<Long> categoryIds) {
        // 清除分类缓存
        if (!Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId()) || CollUtil.isEmpty(categoryIds)) {
            return;
        }
        attrService.removeAttrByCategoryId(categoryIds);
    }

}
