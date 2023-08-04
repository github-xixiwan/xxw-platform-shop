package com.xxw.shop.cache;

import com.xxw.shop.module.common.cache.CacheNames;

public interface PlatformCacheNames extends CacheNames {

    /**
     * 前缀
     */
    String SERVICE_PREFIX = "shop_platform:";

    String PLATFORM_SIMPLE_INFO_KEY = SERVICE_PREFIX + "simple_info";

    /**
     * 缓存配置对象前缀
     */
    String SYS_CONFIG = SERVICE_PREFIX + "config:";

    /**
     * 缓存配置对象前缀
     */
    String SYS_CONFIG_OBJECT = SERVICE_PREFIX + "object:";

}
