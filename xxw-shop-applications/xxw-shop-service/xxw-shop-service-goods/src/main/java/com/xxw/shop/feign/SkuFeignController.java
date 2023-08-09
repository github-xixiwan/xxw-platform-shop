package com.xxw.shop.feign;

import com.xxw.shop.api.goods.feign.SkuFeignClient;
import com.xxw.shop.api.goods.vo.SkuVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.SkuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkuFeignController implements SkuFeignClient {

    @Resource
    private SkuService skuService;

    @Override
    public ServerResponseEntity<SkuVO> getById(Long skuId) {
        return ServerResponseEntity.success(skuService.getSkuBySkuId(skuId));
    }
}
