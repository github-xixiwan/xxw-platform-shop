package com.xxw.shop.config;

import com.xxw.shop.canal.ShopCanalGlue;
import com.xxw.shop.starter.canal.CanalGlue;
import com.xxw.shop.starter.canal.support.processor.CanalBinlogEventProcessorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CanalGlueConfig {

    @Bean
    @Primary
    public CanalGlue canalGlue(CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory) {
        return ShopCanalGlue.of(canalBinlogEventProcessorFactory);
    }
}
