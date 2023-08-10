package com.xxw.shop.stream.produce;

import jakarta.annotation.Resource;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RocketmqSend {

    @Resource
    private StreamBridge streamBridge;

    public boolean orderNotify(List<Long> orderIds) {
        // 一个小时后解锁库存
        Message<List<Long>> message = MessageBuilder.withPayload(orderIds).build();
        return streamBridge.send("order-notify", orderIds);
    }
}
