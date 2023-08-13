package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.goods.vo.CategoryVO;
import com.xxw.shop.cache.GoodsCacheNames;
import com.xxw.shop.constant.AttrType;
import com.xxw.shop.constant.GoodsBusinessError;
import com.xxw.shop.dto.AttrDTO;
import com.xxw.shop.dto.AttrQueryDTO;
import com.xxw.shop.entity.Attr;
import com.xxw.shop.mapper.AttrMapper;
import com.xxw.shop.module.cache.tool.IGlobalRedisCache;
import com.xxw.shop.module.common.cache.CacheNames;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.*;
import com.xxw.shop.vo.AttrCompleteVO;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xxw.shop.entity.table.AttrTableDef.ATTR;
import static com.xxw.shop.entity.table.AttrValueTableDef.ATTR_VALUE;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    @Resource
    private IGlobalRedisCache globalRedisCache;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private AttrCategoryService attrCategoryService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private AttrValueService attrValueService;

    @Resource
    private SpuAttrValueService spuAttrValueService;

    @Override
    public Page<AttrCompleteVO> page(AttrQueryDTO dto) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(ATTR.ALL_COLUMNS);
        queryWrapper.select(ATTR_VALUE.ATTR_VALUE_ID, ATTR_VALUE.VALUE);
        queryWrapper.from(ATTR);
        queryWrapper.leftJoin(ATTR_VALUE).on(ATTR.ATTR_ID.eq(ATTR_VALUE.ATTR_ID));
        queryWrapper.where(ATTR.SHOP_ID.eq(AuthUserContext.get().getTenantId()));
        queryWrapper.and(ATTR.SEARCH_TYPE.eq(dto.getSearchType()));
        queryWrapper.and(ATTR.ATTR_TYPE.eq(dto.getAttrType()));
        queryWrapper.and(ATTR.NAME.eq(dto.getName()).when(StringUtils.isNotBlank(dto.getName())));
        queryWrapper.orderBy(ATTR.ATTR_ID.desc());
        return this.pageAs(new Page<>(dto.getPageNumber(), dto.getPageSize()), queryWrapper, AttrCompleteVO.class);
    }

    private AttrCompleteVO getById(Long attrId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(ATTR.ALL_COLUMNS);
        queryWrapper.select(ATTR_VALUE.ATTR_VALUE_ID, ATTR_VALUE.VALUE);
        queryWrapper.from(ATTR);
        queryWrapper.leftJoin(ATTR_VALUE).on(ATTR.ATTR_ID.eq(ATTR_VALUE.ATTR_ID));
        queryWrapper.where(ATTR.ATTR_ID.eq(attrId));
        AttrCompleteVO attrVO = this.getOneAs(queryWrapper, AttrCompleteVO.class);
        if (Objects.isNull(attrVO)) {
            throw new BusinessException(GoodsBusinessError.GOODS_00001);
        }
        return attrVO;
    }

    @Override
    public AttrCompleteVO getByAttrId(Long attrId) {
        AttrCompleteVO attrVO = getById(attrId);
        if (Objects.equals(attrVO.getAttrType(), AttrType.BASIC.value())) {
            attrVO.setCategories(attrCategoryService.listByAttrId(attrId));
            categoryService.getPathNames(attrVO.getCategories());
        }
        return attrVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttr(AttrDTO dto, List<Long> categoryIds) {
        Attr attr = mapperFacade.map(dto, Attr.class);
        attr.setShopId(AuthUserContext.get().getTenantId());
        this.save(attr);
        Long attrId = attr.getAttrId();
        // 保存属性值
        attrValueService.saveAttrValue(dto.getAttrValues(), attrId);
        // 基本属性关联分类
        if (Objects.equals(AttrType.BASIC.value(), dto.getAttrType())) {
            // 保存属性分类关联信息
            attrCategoryService.saveAttrCategory(attrId, categoryIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAttr(AttrDTO dto, List<Long> categoryIds) {
        Attr attr = mapperFacade.map(dto, Attr.class);
        AttrCompleteVO dbAttr = getById(dto.getAttrId());
        // 保存属性值
        attrValueService.updateAttrValue(dto, dbAttr);
        this.updateById(attr);
        if (Objects.equals(dbAttr.getAttrType(), AttrType.BASIC.value())) {
            // 更新属性分类关联信息
            List<Long> ids = attrCategoryService.updateAttrCategory(dto.getAttrId(), categoryIds);
            // 清除取消关联的分类的数据
            spuAttrValueService.deleteByAttIdAndCategoryIds(dto.getAttrId(), null, ids);
        }
    }

    @Override
    public void deleteById(Long attrId) {
        AttrCompleteVO dbAttr = getByAttrId(attrId);
        if (Objects.equals(dbAttr.getAttrType(), AttrType.BASIC.value())) {
            List<Long> categoryIds =
                    dbAttr.getCategories().stream().map(CategoryVO::getCategoryId).collect(Collectors.toList());
            spuAttrValueService.deleteByAttIdAndCategoryIds(attrId, null, categoryIds);
        }
        this.removeById(attrId);
    }

    @Override
    @Cacheable(cacheNames = GoodsCacheNames.ATTRS_BY_CATEGORY_KEY, key = "#categoryId")
    public List<AttrCompleteVO> getAttrsByCategoryIdAndAttrType(Long categoryId) {
        return mapper.getAttrsByCategoryIdAndAttrType(categoryId);
    }

    @Override
    public List<Long> getAttrOfCategoryIdByAttrId(Long attrId) {
        AttrCompleteVO attr = getById(attrId);
        if (CollUtil.isEmpty(attr.getCategories())) {
            return new ArrayList<>();
        }
        return attr.getCategories().stream().map(CategoryVO::getCategoryId).collect(Collectors.toList());
    }

    @Override
    public void removeAttrByCategoryId(List<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return;
        }
        List<String> keys = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            keys.add(GoodsCacheNames.ATTRS_BY_CATEGORY_KEY + CacheNames.UNION + categoryId);
        }
        globalRedisCache.del(keys.toArray(new String[0]));
    }

    @Override
    public List<AttrCompleteVO> getShopAttrs(Long shopId) {
        return mapper.getShopAttrs(shopId);
    }
}
