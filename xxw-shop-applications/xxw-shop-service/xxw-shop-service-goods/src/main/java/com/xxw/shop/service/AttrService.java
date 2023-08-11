package com.xxw.shop.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.AttrDTO;
import com.xxw.shop.dto.AttrQueryDTO;
import com.xxw.shop.entity.Attr;
import com.xxw.shop.vo.AttrCompleteVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface AttrService extends IService<Attr> {

    Page<AttrCompleteVO> page(AttrQueryDTO dto);

    AttrCompleteVO getByAttrId(Long attrId);

    void saveAttr(AttrDTO dto, List<Long> categoryIds);

    void updateAttr(AttrDTO dto, List<Long> categoryIds);

    void deleteById(Long attrId);

    List<AttrCompleteVO> getAttrsByCategoryIdAndAttrType(Long categoryId);

    List<Long> getAttrOfCategoryIdByAttrId(Long attrId);

    void removeAttrByCategoryId(List<Long> categoryIds);

    List<AttrCompleteVO> getShopAttrs(Long shopId);

}
