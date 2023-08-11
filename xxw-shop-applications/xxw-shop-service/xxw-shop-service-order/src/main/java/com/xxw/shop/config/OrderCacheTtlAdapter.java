package com.xxw.shop.config;

import com.xxw.shop.cache.OrderCacheNames;
import com.xxw.shop.module.cache.adapter.CacheTtlAdapter;
import com.xxw.shop.module.cache.bo.CacheNameWithTtlBO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class OrderCacheTtlAdapter implements CacheTtlAdapter {
    @Override
    public List<CacheNameWithTtlBO> listCacheNameWithTtl() {
        List<CacheNameWithTtlBO> cacheNameWithTtls = new ArrayList<>();
        // 确认订单缓存30分钟
        cacheNameWithTtls.add(new CacheNameWithTtlBO(OrderCacheNames.ORDER_CONFIRM_UUID_KEY, 60 * 30));
        cacheNameWithTtls.add(new CacheNameWithTtlBO(OrderCacheNames.ORDER_CONFIRM_KEY, 60 * 30));
        return cacheNameWithTtls;
    }
}
