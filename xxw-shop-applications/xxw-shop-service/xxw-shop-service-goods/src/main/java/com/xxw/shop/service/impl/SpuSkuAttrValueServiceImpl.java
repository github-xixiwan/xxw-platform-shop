package com.xxw.shop.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.entity.SpuSkuAttrValue;
import com.xxw.shop.mapper.SpuSkuAttrValueMapper;
import com.xxw.shop.service.SpuSkuAttrValueService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.xxw.shop.entity.table.SpuSkuAttrValueTableDef.SPU_SKU_ATTR_VALUE;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class SpuSkuAttrValueServiceImpl extends ServiceImpl<SpuSkuAttrValueMapper, SpuSkuAttrValue> implements SpuSkuAttrValueService {

    @Override
    public void updateBySpuId(Long spuId) {
        SpuSkuAttrValue spuSkuAttrValue = new SpuSkuAttrValue();
        spuSkuAttrValue.setStatus(-1);
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SPU_SKU_ATTR_VALUE.SPU_ID.eq(spuId));
        this.update(spuSkuAttrValue, queryWrapper);
    }

    @Override
    public void changeStatusBySkuId(List<Long> skuIds, Integer status) {
        SpuSkuAttrValue spuSkuAttrValue = new SpuSkuAttrValue();
        spuSkuAttrValue.setStatus(status);
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SPU_SKU_ATTR_VALUE.SKU_ID.in(skuIds));
        this.update(spuSkuAttrValue, queryWrapper);
    }
}
