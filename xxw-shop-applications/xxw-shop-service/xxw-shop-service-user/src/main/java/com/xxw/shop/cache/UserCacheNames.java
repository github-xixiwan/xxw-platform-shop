package com.xxw.shop.cache;

import com.xxw.shop.module.common.cache.CacheNames;

public interface UserCacheNames extends CacheNames {

    /**
     * 前缀
     */
    String SERVICE_PREFIX = "shop_user:";

    /**
     * 店铺分类列表缓存key
     */
    String AREA_INFO_KEY = SERVICE_PREFIX + "area_info";

    /**
     * 店铺分类列表缓存key
     */
    String AREA_KEY = SERVICE_PREFIX + "area";

    /**
     * 用户信息缓存key
     */
    String USER_INFO = SERVICE_PREFIX + "info:";

    /**
     * 用户默认地址缓存key
     */
    String USER_DEFAULT_ADDR = SERVICE_PREFIX + "user_addr:user_id:";

}
