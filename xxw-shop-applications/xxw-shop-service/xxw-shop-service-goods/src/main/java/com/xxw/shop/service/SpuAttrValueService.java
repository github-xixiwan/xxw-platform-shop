package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.entity.SpuAttrValue;
import com.xxw.shop.vo.SpuAttrValueVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SpuAttrValueService extends IService<SpuAttrValue> {

    void updateSpuAttrValue(Long spuId, List<SpuAttrValue> spuAttrValues, List<SpuAttrValueVO> spuAttrValuesDb);

    void saveBatch(Long spuId, List<SpuAttrValue> spuAttrValues);

    void deleteBySpuId(Long spuId);

    void deleteByAttIdAndCategoryIds(Long attrId, List<Long> attrValueId, List<Long> categoryIds);

    List<SpuAttrValueVO> getSpuAttrsBySpuId(Long spuId);

    void batchUpdateSpuAttrValue(List<SpuAttrValue> spuAttrValues);
}
