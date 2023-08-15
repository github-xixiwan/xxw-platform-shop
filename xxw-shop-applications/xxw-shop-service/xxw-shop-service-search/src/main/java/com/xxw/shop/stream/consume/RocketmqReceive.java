package com.xxw.shop.stream.consume;

import com.xxw.shop.starter.canal.CanalGlue;
import jakarta.annotation.Resource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RefreshScope
public class RocketmqReceive {

    @Resource
    private CanalGlue canalGlue;

    @Bean
    public Consumer<String> xxwShop() {
        return message -> {
            canalGlue.process(message);
        };
    }
}
