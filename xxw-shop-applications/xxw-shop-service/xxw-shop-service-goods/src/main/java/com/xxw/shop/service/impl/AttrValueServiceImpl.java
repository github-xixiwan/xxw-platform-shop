package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.constant.AttrType;
import com.xxw.shop.dto.AttrDTO;
import com.xxw.shop.dto.AttrValueDTO;
import com.xxw.shop.entity.AttrValue;
import com.xxw.shop.entity.SpuAttrValue;
import com.xxw.shop.mapper.AttrValueMapper;
import com.xxw.shop.service.AttrValueService;
import com.xxw.shop.service.SpuAttrValueService;
import com.xxw.shop.vo.AttrVO;
import com.xxw.shop.vo.AttrValueVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class AttrValueServiceImpl extends ServiceImpl<AttrValueMapper, AttrValue> implements AttrValueService {

    @Resource
    private SpuAttrValueService spuAttrValueService;

    @Override
    public void saveAttrValue(List<AttrValueDTO> attrValues, Long attrId) {
        if (CollUtil.isEmpty(attrValues)) {
            return;
        }
        List<AttrValue> list = attrValues.stream().map(l -> {
            AttrValue attrValue = new AttrValue();
            attrValue.setAttrId(attrId);
            attrValue.setValue(l.getValue());
            return attrValue;
        }).collect(Collectors.toList());
        this.saveBatch(list);
    }

    private void updateAttrValue(List<AttrValueDTO> attrValues) {
        if (CollUtil.isEmpty(attrValues)) {
            return;
        }
        List<AttrValue> list = attrValues.stream().map(l -> {
            AttrValue attrValue = new AttrValue();
            attrValue.setAttrValueId(l.getAttrValueId());
            attrValue.setAttrId(l.getAttrId());
            attrValue.setValue(l.getValue());
            return attrValue;
        }).collect(Collectors.toList());
        this.updateBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAttrValue(AttrDTO dto, AttrVO dbAttr) {
        List<AttrValueDTO> addAttrValue = new ArrayList<>();
        List<AttrValueDTO> updateAttrValue = new ArrayList<>();
        List<Long> deleteAttrValue =
                dbAttr.getAttrValues().stream().map(AttrValueVO::getAttrValueId).collect(Collectors.toList());
        // 分别筛选出增删改的数据
        dto.getAttrValues().forEach(attrValue -> {
            if (Objects.isNull(attrValue.getAttrValueId())) {
                addAttrValue.add(attrValue);
            } else {
                updateAttrValue.add(attrValue);
                deleteAttrValue.remove(attrValue.getAttrValueId());
            }
        });
        if (CollUtil.isNotEmpty(deleteAttrValue)) {
            if (Objects.equals(dto.getAttrType(), AttrType.BASIC.value())) {
                spuAttrValueService.deleteByAttIdAndCategoryIds(dto.getAttrId(), deleteAttrValue, null);
            }
            this.removeByIds(deleteAttrValue);
        }

        // 新增属性值数据
        saveAttrValue(addAttrValue, dto.getAttrId());
        if (CollUtil.isNotEmpty(updateAttrValue)) {
            updateAttrValue(updateAttrValue);
        }
        if (Objects.equals(dto.getAttrType(), AttrType.BASIC.value())) {
            updateAttrAndAttrValueOfSpuOrSku(updateAttrValue, dto);
        }
    }

    /**
     * 更新属性数据时，更新商品/sku中的属性数据
     * 若不需要同步更新商品/sku中的属性数据，在更新属性数据时，不调用该方法即可，不影响其他流程
     *
     * @param updateAttrValue
     * @param dto
     */
    private void updateAttrAndAttrValueOfSpuOrSku(List<AttrValueDTO> updateAttrValue, AttrDTO dto) {
        if (CollUtil.isEmpty(updateAttrValue)) {
            return;
        }
        List<SpuAttrValue> spuAttrValues = new ArrayList<>(updateAttrValue.size());
        for (AttrValueDTO attrValue : updateAttrValue) {
            SpuAttrValue spuAttrValue = new SpuAttrValue();
            spuAttrValue.setAttrName(dto.getName());
            spuAttrValue.setAttrDesc(dto.getDesc());
            spuAttrValue.setAttrValueName(attrValue.getValue());
            spuAttrValue.setAttrId(dto.getAttrId());
            spuAttrValue.setAttrValueId(attrValue.getAttrValueId());
            spuAttrValues.add(spuAttrValue);
        }
        // 更新数据
        if (CollUtil.isNotEmpty(spuAttrValues)) {
            spuAttrValueService.batchUpdateSpuAttrValue(spuAttrValues);
        }
    }
}
