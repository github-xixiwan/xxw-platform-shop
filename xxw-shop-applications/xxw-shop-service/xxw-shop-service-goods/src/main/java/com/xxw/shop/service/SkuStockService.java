package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.SkuDTO;
import com.xxw.shop.entity.SkuStock;
import com.xxw.shop.vo.SkuStockVO;
import com.xxw.shop.api.goods.vo.SkuVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SkuStockService extends IService<SkuStock> {

    List<SkuStockVO> listBySkuList(List<SkuVO> skuVOList);

    void updateBatch(List<SkuDTO> skuList);
}
