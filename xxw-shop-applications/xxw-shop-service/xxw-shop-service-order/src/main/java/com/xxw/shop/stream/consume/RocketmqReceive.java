package com.xxw.shop.stream.consume;

import cn.hutool.json.JSONUtil;
import com.xxw.shop.service.OrderInfoService;
import com.xxw.shop.stream.produce.RocketmqSend;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
public class RocketmqReceive {

    @Resource
    private RocketmqSend rocketmqSend;

    @Resource
    private OrderInfoService orderInfoService;

    @Bean
    public Consumer<List<Long>> orderCancel() {
        return message -> {
            log.info("orderCancel 接收 orderIds：{}", JSONUtil.toJsonStr(message));
            orderInfoService.cancelOrderAndGetCancelOrderIds(message);
        };
    }

    @Bean
    public Consumer<List<Long>> orderNotify() {
        return message -> {
            log.info("orderNotify 接收 orderIds：{}", JSONUtil.toJsonStr(message));
            orderInfoService.updateByToPaySuccess(message);
            // 发送消息，订单支付成功 通知库存扣减
            rocketmqSend.orderNotifyStock(message);
        };
    }
}
