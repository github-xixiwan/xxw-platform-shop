package com.xxw.shop.module.cache.config;

import com.xxw.shop.module.cache.adapter.CacheTtlAdapter;
import com.xxw.shop.module.cache.bo.CacheNameWithTtlBO;
import com.xxw.shop.module.cache.tool.IGlobalRedisCache;
import com.xxw.shop.module.cache.tool.IGlobalRedisCacheManager;
import com.xxw.shop.module.cache.tool.impl.GlobalRedisCacheManagerTool;
import com.xxw.shop.module.cache.tool.impl.GlobalRedisCacheTool;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap(CacheTtlAdapter cacheTtlAdapter) {
        if (cacheTtlAdapter == null) {
            return Collections.emptyMap();
        }
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(16);

        for (CacheNameWithTtlBO cacheNameWithTtlBO : cacheTtlAdapter.listCacheNameWithTtl()) {
            redisCacheConfigurationMap.put(cacheNameWithTtlBO.getCacheName(),
                    getRedisCacheConfigurationWithTtl(cacheNameWithTtlBO.getTtl()));
        }
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration =
                redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json())).entryTtl(Duration.ofSeconds(seconds));
        return redisCacheConfiguration;
    }

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory,
                                     CacheTtlAdapter cacheTtlAdapter) {
        RedisCacheManager redisCacheManager =
                new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory),
                // 默认策略，未配置的 key 会使用这个
                this.getRedisCacheConfigurationWithTtl(3600),
                // 指定 key 策略
                this.getRedisCacheConfigurationMap(cacheTtlAdapter));

        redisCacheManager.setTransactionAware(true);
        return redisCacheManager;
    }

    @Bean
    public IGlobalRedisCache cache(RedisTemplate<String, Object> redisTemplate) {
        return new GlobalRedisCacheTool(redisTemplate);
    }

    @Bean
    public IGlobalRedisCacheManager cache(CacheManager cacheManager) {
        return new GlobalRedisCacheManagerTool(cacheManager);
    }
}
