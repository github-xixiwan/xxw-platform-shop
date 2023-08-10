package com.xxw.shop.stream.consume;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class RocketmqReceive {

//    @Resource
//    private OrderInfoService orderInfoService;
//
//    @Bean
//    public Consumer<List<Long>> orderCancel() {
//        return message -> {
//            orderInfoService.cancelOrderAndGetCancelOrderIds(message);
//        };
//    }
}
