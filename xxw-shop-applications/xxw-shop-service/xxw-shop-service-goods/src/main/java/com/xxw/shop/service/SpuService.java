package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.SpuDTO;
import com.xxw.shop.entity.Spu;
import com.xxw.shop.vo.EsGoodsVO;
import com.xxw.shop.vo.SpuVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SpuService extends IService<Spu> {

    SpuVO getBySpuId(Long spuId);

    void removeSpuCacheBySpuId(Long spuId);

    void batchRemoveSpuCacheBySpuId(List<Long> spuIds);

    void changeSpuStatus(Long spuId, Integer status);

    void saveSpu(SpuDTO spuDTO);

    void updateSpu(SpuDTO spuDTO);

    void deleteById(Long spuId);

    void updateSpuOrSku(SpuDTO spuDTO);

    void updateSpuUpdateTime(List<Long> spuIds, List<Long> categoryIds);

    EsGoodsVO loadEsGoodsVO(Long spuId);

    List<Long> getSpuIdsByCondition(List<Long> shopCategoryIds, List<Long> categoryIds, Long brandId, Long shopId);

    List<SpuVO> listBySpuIds(List<Long> spuIds, String prodName, Integer isFailure);

    void batchChangeSpuStatusByCids(List<Long> cidList, Long shopId, Integer status);

    long getUseNum(Long brandId);
}
