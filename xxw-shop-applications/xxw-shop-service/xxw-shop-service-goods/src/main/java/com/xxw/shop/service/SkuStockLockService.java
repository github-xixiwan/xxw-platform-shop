package com.xxw.shop.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.api.goods.dto.SkuStockLockDTO;
import com.xxw.shop.dto.SkuStockLockQueryDTO;
import com.xxw.shop.entity.SkuStockLock;
import com.xxw.shop.module.common.response.ServerResponseEntity;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SkuStockLockService extends IService<SkuStockLock> {

    Page<SkuStockLock> page(SkuStockLockQueryDTO dto);

    SkuStockLock getSkuStockLockById(Long id);

    ServerResponseEntity<Void> lock(List<SkuStockLockDTO> skuStockLocksParam);

    void stockUnlock(List<Long> orderIds);

    void markerStockUse(List<Long> orderIds);
}
