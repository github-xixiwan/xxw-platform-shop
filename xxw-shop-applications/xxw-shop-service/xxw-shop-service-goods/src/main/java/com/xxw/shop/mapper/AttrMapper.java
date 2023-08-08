package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.Attr;
import com.xxw.shop.vo.AttrVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface AttrMapper extends BaseMapper<Attr> {

    /**
     * 根据分类和属性类型，获取对应的属性列表
     *
     * @param categoryId
     * @return
     */
    List<AttrVO> getAttrsByCategoryIdAndAttrType(@Param("categoryId") Long categoryId);

    /**
     * 获取店铺中的销售属性
     * @param shopId
     * @return 销售属性列表
     */
    List<AttrVO> getShopAttrs(Long shopId);
}
