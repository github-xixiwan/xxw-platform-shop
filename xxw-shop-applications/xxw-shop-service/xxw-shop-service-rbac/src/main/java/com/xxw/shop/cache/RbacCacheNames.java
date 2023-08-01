package com.xxw.shop.cache;

import com.xxw.shop.module.web.cache.CacheNames;

public interface RbacCacheNames extends CacheNames {

    /**
     * 前缀
     */
    String SERVICE_PREFIX = "shop_rbac:";

    /**
     * uri对应的权限缓存key
     */
    String MENU_LIST_KEY = SERVICE_PREFIX + "menu:list:";

    /**
     * 菜单id key
     */
    String MENU_ID_LIST_KEY = SERVICE_PREFIX + "menu:id_list:";

    /**
     * uri对应的权限缓存key
     */
    String URI_PERMISSION_KEY = SERVICE_PREFIX + "permission:uri_permissions:";

    /**
     * 用户拥有的权限列表缓存key
     */
    String USER_PERMISSIONS_KEY = SERVICE_PREFIX + "permission:user_permissions:";

}
