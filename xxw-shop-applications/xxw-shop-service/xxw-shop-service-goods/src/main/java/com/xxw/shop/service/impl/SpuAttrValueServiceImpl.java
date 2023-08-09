package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.cache.GoodsCacheNames;
import com.xxw.shop.entity.SpuAttrValue;
import com.xxw.shop.mapper.SpuAttrValueMapper;
import com.xxw.shop.module.cache.tool.IGlobalRedisCache;
import com.xxw.shop.module.common.cache.CacheNames;
import com.xxw.shop.service.SpuAttrValueService;
import com.xxw.shop.service.SpuService;
import com.xxw.shop.api.goods.vo.SpuAttrValueVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xxw.shop.entity.table.SpuAttrValueTableDef.SPU_ATTR_VALUE;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class SpuAttrValueServiceImpl extends ServiceImpl<SpuAttrValueMapper, SpuAttrValue> implements SpuAttrValueService {

    @Resource
    private IGlobalRedisCache globalRedisCache;

    @Resource
    private SpuService spuService;

    @Override
    public void updateSpuAttrValue(Long spuId, List<SpuAttrValue> spuAttrValues, List<SpuAttrValueVO> spuAttrValuesDb) {
        List<Long> spuAttrValueIdsDb =
                spuAttrValuesDb.stream().map(SpuAttrValueVO::getSpuAttrValueId).collect(Collectors.toList());
        List<SpuAttrValue> updateList = new ArrayList<>();
        List<SpuAttrValue> saveList = new ArrayList<>();
        List<Long> spuAttrValueIds = new ArrayList<>();
        for (SpuAttrValue spuAttrValue : spuAttrValues) {
            if (spuAttrValueIdsDb.contains(spuAttrValue.getSpuAttrValueId())) {
                if (Objects.nonNull(spuAttrValue.getAttrValueName()) || Objects.nonNull(spuAttrValue.getAttrValueId())) {
                    updateList.add(spuAttrValue);
                }
                spuAttrValueIds.add(spuAttrValue.getSpuAttrValueId());
                continue;
            }
            spuAttrValue.setSpuId(spuId);
            saveList.add(spuAttrValue);
        }
        // 保存新增的关联属性
        if (CollUtil.isNotEmpty(saveList)) {
            saveBatch(spuId, saveList);
        }
        // 更新属性
        if (CollUtil.isNotEmpty(updateList)) {
            this.updateBatch(updateList);
        }
        // 删除属性
        spuAttrValueIdsDb.removeAll(spuAttrValueIds);
        if (CollUtil.isNotEmpty(spuAttrValueIdsDb)) {
            this.removeByIds(spuAttrValueIdsDb);
        }
    }

    @Override
    public void saveBatch(Long spuId, List<SpuAttrValue> spuAttrValues) {
        if (CollUtil.isEmpty(spuAttrValues)) {
            return;
        }
        spuAttrValues.forEach(l -> {
            l.setSpuId(spuId);
        });
        this.saveBatch(spuAttrValues);
    }

    @Override
    public void deleteBySpuId(Long spuId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SPU_ATTR_VALUE.SPU_ID.eq(spuId));
        this.remove(queryWrapper);
    }

    @Override
    public void deleteByAttIdAndCategoryIds(Long attrId, List<Long> attrValueId, List<Long> categoryIds) {
        if (CollUtil.isEmpty(attrValueId) && CollUtil.isEmpty(categoryIds)) {
            return;
        }
        updateSpu(attrValueId, categoryIds);
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SPU_ATTR_VALUE.ATTR_ID.eq(attrId));
        queryWrapper.and(SPU_ATTR_VALUE.ATTR_VALUE_ID.in(attrValueId));
        this.remove(queryWrapper);
    }

    @Override
    public List<SpuAttrValueVO> getSpuAttrsBySpuId(Long spuId) {
        return mapper.getSpuAttrsBySpuId(spuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateSpuAttrValue(List<SpuAttrValue> spuAttrValues) {
        mapper.batchUpdateSpuAttrValue(spuAttrValues);
        List<Long> attrValueIds = spuAttrValues.stream().map(SpuAttrValue::getAttrValueId).collect(Collectors.toList());
        updateSpu(attrValueIds, null);
    }

    private void updateSpu(List<Long> attrValueIds, List<Long> categoryIds) {
        List<Long> spuIds = null;
        if (CollUtil.isNotEmpty(attrValueIds)) {
            spuIds = mapper.getShopIdByAttrValueIds(attrValueIds);
            mapper.updateSpuUpdateTime(spuIds, null);
        } else if (CollUtil.isNotEmpty(categoryIds)) {
            spuIds = spuService.getSpuIdsByCondition(null, categoryIds, null, null);
            mapper.updateSpuUpdateTime(null, categoryIds);
        }
        if (CollUtil.isEmpty(spuIds)) {
            return;
        }
        List<String> spuKeys = new ArrayList<>();
        List<String> spuExtensionKeys = new ArrayList<>();
        for (Long key : spuIds) {
            spuKeys.add(GoodsCacheNames.SPU_KEY + CacheNames.UNION + key);
            spuExtensionKeys.add(GoodsCacheNames.SPU_EXTENSION_KEY + CacheNames.UNION + key);
        }
        globalRedisCache.del(spuKeys.toArray(new String[0]));
        globalRedisCache.del(spuExtensionKeys.toArray(new String[0]));
    }
}
