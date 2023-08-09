package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.entity.SpuSkuAttrValue;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SpuSkuAttrValueService extends IService<SpuSkuAttrValue> {

    void updateBySpuId(Long spuId);

    void changeStatusBySkuId(List<Long> skuIds, Integer status);
}
