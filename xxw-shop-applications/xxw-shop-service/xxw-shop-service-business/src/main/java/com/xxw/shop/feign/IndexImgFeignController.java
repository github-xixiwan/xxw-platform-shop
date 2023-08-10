package com.xxw.shop.feign;

import com.xxw.shop.api.business.feign.IndexImgFeignClient;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.IndexImgService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexImgFeignController implements IndexImgFeignClient {

    @Resource
    private IndexImgService indexImgService;

    @Override
    public ServerResponseEntity<Void> deleteBySpuId(Long spuId, Long shopId) {
        indexImgService.deleteBySpuId(spuId, shopId);
        return ServerResponseEntity.success();
    }
}
