package com.xxw.shop.stream.consume;

import com.xxw.shop.service.OrderInfoService;
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
    private OrderInfoService orderInfoService;

    @Bean
    public Consumer<List<Long>> orderCancel() {
        return message -> {
            orderInfoService.cancelOrderAndGetCancelOrderIds(message);
        };
    }
}
