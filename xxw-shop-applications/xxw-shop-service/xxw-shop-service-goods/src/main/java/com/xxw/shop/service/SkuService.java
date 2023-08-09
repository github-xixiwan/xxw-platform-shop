package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.SkuDTO;
import com.xxw.shop.dto.SpuDTO;
import com.xxw.shop.entity.Sku;
import com.xxw.shop.vo.SkuConsumerVO;
import com.xxw.shop.api.goods.vo.SkuVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SkuService extends IService<Sku> {

    void saveSku(Long spuId, List<SkuDTO> skuList);

    void updateSku(Long spuId, List<SkuDTO> skuList);

    List<SkuVO> listBySpuId(Long spuId);

    void removeSkuCacheBySpuIdOrSkuIds(Long spuId, List<Long> skuIds);

    void deleteBySpuId(Long spuId);

    List<SkuVO> listBySpuIdAndExtendInfo(Long spuId);

    SkuVO getSkuBySkuId(Long skuId);

    void updateAmountOrStock(SpuDTO spuDTO);

    List<SkuConsumerVO> getSkuBySpuId(Long spuId);
}
