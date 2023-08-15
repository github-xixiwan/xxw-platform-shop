package com.xxw.shop.stream.produce;

import jakarta.annotation.Resource;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class RocketmqSend {

    @Resource
    private StreamBridge streamBridge;
}
