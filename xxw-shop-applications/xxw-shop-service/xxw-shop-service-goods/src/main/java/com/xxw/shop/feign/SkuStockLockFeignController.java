package com.xxw.shop.feign;

import com.xxw.shop.api.goods.dto.SkuStockLockDTO;
import com.xxw.shop.api.goods.feign.SkuStockLockFeignClient;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.SkuStockLockService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkuStockLockFeignController implements SkuStockLockFeignClient {

    @Resource
    private SkuStockLockService skuStockLockService;

    @Override
    public ServerResponseEntity<Void> lock(List<SkuStockLockDTO> skuStockLocksParam) {
        return skuStockLockService.lock(skuStockLocksParam);
    }
}
