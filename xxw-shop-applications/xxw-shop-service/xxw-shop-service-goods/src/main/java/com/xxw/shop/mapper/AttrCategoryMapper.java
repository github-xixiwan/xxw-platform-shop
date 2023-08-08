package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.AttrCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface AttrCategoryMapper extends BaseMapper<AttrCategory> {

    /**
     * 根据属性id，获取属性关联的分类id列表
     *
     * @param attrId 属性id
     * @return 分类id列表
     */
    List<Long> getCategoryIdsByAttrId(@Param("attrId") Long attrId);
}
