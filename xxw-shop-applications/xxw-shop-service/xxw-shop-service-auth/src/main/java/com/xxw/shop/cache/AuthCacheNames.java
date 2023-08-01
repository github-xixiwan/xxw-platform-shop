package com.xxw.shop.cache;

import com.xxw.shop.module.web.cache.CacheNames;

public interface AuthCacheNames extends CacheNames {

    /**
     * 前缀
     */
    String SERVICE_PREFIX = "shop_auth:";

    /**
     * 保存token 缓存使用key
     */
    String ACCESS = SERVICE_PREFIX + "access:";

    /**
     * 根据uid获取保存的token key缓存使用的key
     */
    String UID_TO_ACCESS = SERVICE_PREFIX + "uid_to_access:";

    /**
     * 刷新token 缓存使用key
     */
    String REFRESH_TO_ACCESS = SERVICE_PREFIX + "refresh_to_access:";

}
