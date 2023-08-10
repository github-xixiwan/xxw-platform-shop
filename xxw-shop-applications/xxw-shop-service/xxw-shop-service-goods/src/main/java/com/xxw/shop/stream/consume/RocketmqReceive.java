package com.xxw.shop.stream.consume;

import com.xxw.shop.service.SkuStockLockService;
import jakarta.annotation.Resource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@RefreshScope
public class RocketmqReceive {

    @Resource
    private SkuStockLockService skuStockLockService;

    @Bean
    public Consumer<List<Long>> stockUnlock() {
        return message -> {
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
