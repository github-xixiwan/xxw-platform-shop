package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.entity.CategoryBrand;
import com.xxw.shop.mapper.CategoryBrandMapper;
import com.xxw.shop.service.CategoryBrandService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.xxw.shop.entity.table.CategoryBrandTableDef.CATEGORY_BRAND;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements CategoryBrandService {

    @Override
    public void deleteByBrandId(Long brandId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(CATEGORY_BRAND.BRAND_ID.eq(brandId));
        this.remove(queryWrapper);
    }

    @Override
    public void saveByCategoryIds(Long brandId, List<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return;
        }
        List<CategoryBrand> list = categoryIds.stream().map(l -> {
            CategoryBrand categoryBrand = new CategoryBrand();
            categoryBrand.setBrandId(brandId);
            categoryBrand.setCategoryId(l);
            return categoryBrand;
        }).collect(Collectors.toList());
        this.saveBatch(list);
    }

    @Override
    public void updateByCategoryIds(Long brandId, List<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return;
        }
        List<Long> categoryIdDb = getCategoryIdBrandId(brandId);
        List<Long> addList = new ArrayList<>();
        categoryIds.forEach(categoryId -> {
            if (!categoryIdDb.contains(categoryId)) {
                addList.add(categoryId);
            }
        });
        if (CollUtil.isNotEmpty(addList)) {
            saveByCategoryIds(brandId, addList);
        }
        categoryIdDb.removeAll(categoryIds);
        if (CollUtil.isNotEmpty(categoryIdDb)) {
            QueryWrapper queryWrapper = QueryWrapper.create();
            queryWrapper.where(CATEGORY_BRAND.BRAND_ID.eq(brandId));
            queryWrapper.and(CATEGORY_BRAND.CATEGORY_ID.in(categoryIdDb));
            this.remove(queryWrapper);
        }
    }

    @Override
    public List<Long> getCategoryIdBrandId(Long brandId) {
        return mapper.getCategoryIdsByBrandId(brandId);
    }
}
