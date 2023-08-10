package com.xxw.shop.cache;

import com.xxw.shop.module.common.cache.CacheNames;

public interface BusinessCacheNames extends CacheNames {

    /**
     * 前缀
     */
    String SERVICE_PREFIX = "shop_business:";

    String HOT_SEARCH_LIST_KEY = SERVICE_PREFIX + "hot_search_list";

    /**
     * 店铺分类列表缓存key
     */
    String INDEX_IMG_KEY = SERVICE_PREFIX + "index_img";

    /**
     * 店铺分类列表缓存key
     */
    String SHOP_DETAIL_ID_KEY = SERVICE_PREFIX + "shop_detail:getById:";
}
