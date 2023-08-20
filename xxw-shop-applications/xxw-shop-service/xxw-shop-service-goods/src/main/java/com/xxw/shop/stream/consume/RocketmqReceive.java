package com.xxw.shop.stream.consume;

import com.xxw.shop.module.common.json.JsonUtil;
import com.xxw.shop.service.SkuStockLockService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class RocketmqReceive {

    @Resource
    private SkuStockLockService skuStockLockService;

    @Bean
    public Consumer<List<Long>> stockUnlock() {
        return message -> {
            System.out.println("2222222222 30秒解锁:" + JsonUtil.toJson(message));
            skuStockLockService.stockUnlock(message);
        };
    }

    @Bean
    public Consumer<List<Long>> orderNotifyStock() {
        return message -> {
            skuStockLockService.markerStockUse(message);
        };
    }
}
