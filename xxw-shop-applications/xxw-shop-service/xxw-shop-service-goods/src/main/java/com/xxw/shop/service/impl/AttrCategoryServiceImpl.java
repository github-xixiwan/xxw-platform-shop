package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.entity.AttrCategory;
import com.xxw.shop.mapper.AttrCategoryMapper;
import com.xxw.shop.service.AttrCategoryService;
import com.xxw.shop.api.goods.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.xxw.shop.entity.table.AttrCategoryTableDef.ATTR_CATEGORY;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class AttrCategoryServiceImpl extends ServiceImpl<AttrCategoryMapper, AttrCategory> implements AttrCategoryService {

    @Override
    public void saveAttrCategory(Long attrId, List<Long> categoryIds) {
        List<AttrCategory> list = categoryIds.stream().map(l -> {
            AttrCategory attrCategory = new AttrCategory();
            attrCategory.setAttrId(attrId);
            attrCategory.setCategoryId(l);
            return attrCategory;
        }).collect(Collectors.toList());
        this.saveBatch(list);
    }

    @Override
    public List<Long> updateAttrCategory(Long attrId, List<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return new ArrayList<>();
        }
        List<Long> dbCategoryIds = mapper.getCategoryIdsByAttrId(attrId);
        List<Long> addList = new ArrayList<>(categoryIds.size());
        addList.addAll(categoryIds);
        addList.removeAll(dbCategoryIds);
        if (CollUtil.isNotEmpty(addList)) {
            saveAttrCategory(attrId, addList);
        }
        dbCategoryIds.removeAll(categoryIds);
        if (CollUtil.isNotEmpty(dbCategoryIds)) {
            QueryWrapper queryWrapper = QueryWrapper.create();
            queryWrapper.where(ATTR_CATEGORY.ATTR_ID.eq(attrId));
            queryWrapper.and(ATTR_CATEGORY.CATEGORY_ID.in(dbCategoryIds));
            this.remove(queryWrapper);
        }
        return dbCategoryIds;
    }

    @Override
    public List<CategoryVO> listByAttrId(Long attrId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(ATTR_CATEGORY.ATTR_ID.eq(attrId));
        return this.listAs(queryWrapper, CategoryVO.class);
    }
}
