package com.xxw.shop.cache;

import com.xxw.shop.module.common.cache.CacheNames;

public interface OrderCacheNames extends CacheNames {

    /**
     * 前缀
     */
    String SERVICE_PREFIX = "shop_order:";

    /**
     * 订单uuid
     */
    String ORDER_CONFIRM_UUID_KEY = SERVICE_PREFIX + "order:uuid_confirm";

    /**
     * 确认订单信息缓存
     */
    String ORDER_CONFIRM_KEY = SERVICE_PREFIX + "order:confirm";
}
