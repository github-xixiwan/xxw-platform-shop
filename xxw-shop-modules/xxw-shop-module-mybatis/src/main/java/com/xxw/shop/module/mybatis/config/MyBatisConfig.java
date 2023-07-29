package com.xxw.shop.module.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xxw.shop.**.mapper")
public class MyBatisConfig {

}
