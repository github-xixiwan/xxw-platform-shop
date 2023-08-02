package com.xxw.shop.config;

import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import com.xxw.shop.service.impl.CaptchaCacheServiceRedisImpl;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableConfigurationProperties(AjCaptchaProperties.class)
public class CaptchaConfig {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean(name = "AjCaptchaCacheService")
    @Primary
    public CaptchaCacheService captchaCacheService(AjCaptchaProperties config) {
        //缓存类型redis/local/....
        CaptchaCacheService ret = CaptchaServiceFactory.getCache(config.getCacheType().name());
        if (ret instanceof CaptchaCacheServiceRedisImpl) {
            ((CaptchaCacheServiceRedisImpl) ret).setStringRedisTemplate(stringRedisTemplate);
        }
        return ret;
    }
}