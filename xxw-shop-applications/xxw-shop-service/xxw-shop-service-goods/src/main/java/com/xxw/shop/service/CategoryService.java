package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.entity.Category;
import com.xxw.shop.vo.CategoryVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface CategoryService extends IService<Category> {

    CategoryVO getCategoryId(Long categoryId);

    void saveCategory(Category category);

    void updateCategory(Category category);

    void deleteById(Long categoryId);

    List<CategoryVO> list(Long shopId);

    List<Long> listCategoryId(Long shopId, Long parentId);

    void removeCategoryCache(Long shopId, Long parentId);

    void getPathNames(List<CategoryVO> categorys);

    CategoryVO getPathNameByCategoryId(Long categoryId);

    List<CategoryVO> categoryList(Long shopId, Long parentId);

    List<CategoryVO> listByShopIdAndParenId(Long shopId, Long parentId);

    List<CategoryVO> shopCategoryList(Long shopId);

    List<Category> getChildCategory(Long categoryId);

    void updateBatchOfStatus(List<Long> updateList, Integer status);
}
