package com.xxw.shop.module.cache.tool;

import org.springframework.cache.CacheManager;

public interface IGlobalRedisCacheManager {

    <T> T getCache(String cacheName, String key);

    void putCache(String cacheName, String key, Object value);

    void evictCache(String cacheName, String key);

    /**
     * 返回当前CacheManager
     *
     * @return
     */
    CacheManager getCacheManager();
}
