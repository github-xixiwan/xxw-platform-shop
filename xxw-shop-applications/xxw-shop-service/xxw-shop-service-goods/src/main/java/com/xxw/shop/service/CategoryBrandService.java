package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.entity.CategoryBrand;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface CategoryBrandService extends IService<CategoryBrand> {

    void deleteByBrandId(Long brandId);

    void saveByCategoryIds(Long brandId, List<Long> categoryIds);

    void updateByCategoryIds(Long brandId, List<Long> categoryIds);

    List<Long> getCategoryIdBrandId(Long brandId);
}
