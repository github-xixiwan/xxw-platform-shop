package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.AttrDTO;
import com.xxw.shop.dto.AttrValueDTO;
import com.xxw.shop.entity.AttrValue;
import com.xxw.shop.vo.AttrCompleteVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface AttrValueService extends IService<AttrValue> {

    void saveAttrValue(List<AttrValueDTO> attrValues, Long attrId);

    void updateAttrValue(AttrDTO dto, AttrCompleteVO dbAttr);
}
