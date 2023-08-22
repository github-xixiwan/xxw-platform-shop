package com.xxw.shop.starter.captcha.config;

import com.xingyuv.captcha.properties.AjCaptchaProperties;
import com.xingyuv.captcha.service.CaptchaCacheService;
import com.xingyuv.captcha.service.impl.CaptchaServiceFactory;
import com.xxw.shop.starter.captcha.service.CaptchaCacheServiceRedisImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
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
