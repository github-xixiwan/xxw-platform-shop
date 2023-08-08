package com.xxw.shop.module.cache.tool.impl;

import com.xxw.shop.module.cache.tool.IGlobalRedisCacheManager;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class GlobalRedisCacheManagerTool implements IGlobalRedisCacheManager {

    private CacheManager cacheManager;

    public GlobalRedisCacheManagerTool(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <T> T getCache(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (valueWrapper == null) {
            return null;
        }
        return (T) valueWrapper.get();
    }

    @Override
    public void putCache(String cacheName, String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    @Override
    public void evictCache(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }

    @Override
    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

}
