package com.xxw.shop.module.web.cache;

public interface CacheNames extends RbacCacheNames {

    /**
     * 参考CacheKeyPrefix
     * cacheNames 与 key 之间的默认连接字符
     */
    String UNION = "::";

    /**
     * key内部的连接字符（自定义）
     */
    String UNION_KEY = ":";

}
