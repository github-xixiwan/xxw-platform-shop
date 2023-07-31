package com.xxw.shop.cache;

import com.xxw.shop.module.web.cache.CacheNames;

public interface RbacCacheNames extends CacheNames {

    /**
     * 前缀
     */
    String SERVICE_RBAC_PREFIX = "service_rbac:";


    String SERVICE_RBAC_MENU_BIZ_TYPE_KEY = SERVICE_RBAC_PREFIX + "menu_biz_type:";

    String SERVICE_RBAC_MENU_USER_ID_KEY = SERVICE_RBAC_PREFIX + "menu_user_id:";

    String SERVICE_RBAC_MENU_PERMISSION_BIZ_TYPE_KEY = SERVICE_RBAC_PREFIX + "menu_permission_biz_type:";

    String SERVICE_RBAC_MENU_PERMISSION_BIZ_TYPE_KEY = SERVICE_RBAC_PREFIX + "menu_permission_biz_type:";

    String SERVICE_RBAC_MENU_PERMISSION_BIZ_TYPE_USER_ID_KEY = SERVICE_RBAC_PREFIX + "menu_permission_biz_type_user_id:";

}
