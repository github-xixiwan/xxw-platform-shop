package com.xxw.shop.cache;

import com.xxw.shop.module.web.cache.CacheNames;

public interface AuthCacheNames extends CacheNames {

    /**
     * 前缀
     */
    String SERVICE_PREFIX = "service_auth:";


    String SERVICE_MENU_BIZ_TYPE_KEY = SERVICE_PREFIX + "menu_biz_type:";

    String SERVICE_MENU_USER_ID_KEY = SERVICE_PREFIX + "menu_user_id:";

    String SERVICE_MENU_PERMISSION_BIZ_TYPE_KEY = SERVICE_PREFIX + "menu_permission_biz_type:";

    String SERVICE_MENU_PERMISSION_BIZ_TYPE_USER_ID_KEY = SERVICE_PREFIX + "menu_permission_biz_type_user_id:";

}
