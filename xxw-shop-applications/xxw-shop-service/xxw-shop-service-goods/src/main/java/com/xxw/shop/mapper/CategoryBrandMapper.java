package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.CategoryBrand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface CategoryBrandMapper extends BaseMapper<CategoryBrand> {

    /**
     * 根据品牌id获取关联的分类id
     *
     * @param brandId 品牌id
     * @return 分类id列表
     */
    List<Long> getCategoryIdsByBrandId(@Param("brandId") Long brandId);

}
