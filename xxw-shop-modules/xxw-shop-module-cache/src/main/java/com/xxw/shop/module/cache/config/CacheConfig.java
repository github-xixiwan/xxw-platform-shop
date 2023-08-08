package com.xxw.shop.module.cache.config;

import com.xxw.shop.module.cache.tool.IGlobalRedisCache;
import com.xxw.shop.module.cache.tool.IGlobalRedisCacheManager;
import com.xxw.shop.module.cache.tool.impl.GlobalRedisCacheManagerTool;
import com.xxw.shop.module.cache.tool.impl.GlobalRedisCacheTool;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

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

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration cacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        return RedisCacheManager.builder(lettuceConnectionFactory).cacheDefaults(cacheConfiguration).build();
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
