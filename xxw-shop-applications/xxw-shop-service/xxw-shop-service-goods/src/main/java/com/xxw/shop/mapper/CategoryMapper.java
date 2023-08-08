package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 获取分类被关联的数量
     *
     * @param categoryId
     * @return
     */
    int getCategoryUseNum(@Param("categoryId") Long categoryId);

    /**
     * 获取分类id列表
     *
     * @param shopId   店铺id
     * @param parentId
     * @return
     */
    List<Long> listCategoryId(@Param("shopId") Long shopId, @Param("parentId") Long parentId);

    /**
     * 根据分类id 获取分类下的子分类
     *
     * @param categoryId
     * @return
     */
    List<Category> getChildCategory(@Param("categoryId") Long categoryId);

    /**
     * 批量更新分类状态（启用、禁用）
     *
     * @param categoryIds
     * @param status
     * @return
     */
    int updateBatchOfStatus(@Param("categoryIds") List<Long> categoryIds, @Param("status") Integer status);
}
