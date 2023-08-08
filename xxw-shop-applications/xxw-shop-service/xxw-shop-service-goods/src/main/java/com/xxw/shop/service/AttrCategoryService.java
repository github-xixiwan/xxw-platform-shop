package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.entity.AttrCategory;
import com.xxw.shop.vo.CategoryVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface AttrCategoryService extends IService<AttrCategory> {

    void saveAttrCategory(Long attrId, List<Long> categoryIds);

    List<Long> updateAttrCategory(Long attrId, List<Long> categoryIds);

    List<CategoryVO> listByAttrId(Long attrId);

}
