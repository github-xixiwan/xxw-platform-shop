package com.xxw.shop.stream.produce;

import com.xxw.shop.module.common.json.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RocketmqSend {

    @Resource
    private StreamBridge streamBridge;

    public boolean orderNotify(List<Long> orderIds) {
        log.info("stockUnorderNotifylock 发送 orderIds：{}", JsonUtil.toJson(orderIds));
        Message<List<Long>> message = MessageBuilder.withPayload(orderIds).build();
        return streamBridge.send("order-notify", orderIds);
    }
}
