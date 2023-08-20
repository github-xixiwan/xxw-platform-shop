package com.xxw.shop.stream.produce;

import com.xxw.shop.module.common.json.JsonUtil;
import jakarta.annotation.Resource;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RocketmqSend {

    @Resource
    private StreamBridge streamBridge;

    public boolean stockUnlock(List<Long> orderIds) {
        System.out.println("1111111 30秒解锁库存:" + JsonUtil.toJson(orderIds));
        // 一个小时后解锁库存
        Message<List<Long>> message = MessageBuilder.withPayload(orderIds)
                //设置延时等级1~18 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, 4).build();
        return streamBridge.send("stock-unlock", orderIds);
    }
}
