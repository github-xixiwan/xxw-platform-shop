package com.xxw.shop.stream.consume;

import com.xxw.shop.starter.canal.CanalGlue;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RefreshScope
public class RocketmqReceive {

    @Resource
    private CanalGlue canalGlue;

    @Bean
    public Consumer<String> xxwShopCanal() {
        return message -> {
            try {
                log.info("canal同步 请求：{}", message);
                canalGlue.process(message);
            } catch (Exception e) {
                log.error("canal同步异常 请求：{} 错误：{}", message, ExceptionUtils.getStackTrace(e));
            }
        };
    }
}
