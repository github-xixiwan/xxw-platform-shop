package com.xxw.shop.cache;

import com.xxw.shop.module.common.cache.CacheNames;

public interface GoodsCacheNames extends CacheNames {

    /**
     * 前缀
     */
    String SERVICE_PREFIX = "shop_goods:";

    /**
     * 分类下的属性列表缓存key
     */
    String ATTRS_BY_CATEGORY_KEY = SERVICE_PREFIX + "attrs_by_category:list:";

    /**
     * 置顶品牌列表信息缓存key
     */
    String BRAND_TOP = SERVICE_PREFIX + "brand_top:list";

    /**
     * 分类下的品牌列表缓存key
     */
    String BRAND_LIST_BY_CATEGORY = SERVICE_PREFIX + "brand_list_by_category:id:";

    /**
     * 根据店铺id和上级id，获取分类列表缓存key
     */
    String CATEGORY_LIST_BY_PARENT_ID_AND_SHOP_ID = SERVICE_PREFIX + "category_list:shopIdAndParentId:";

    /**
     * 根据店铺id和上级id，获取分类列表缓存key
     */
    String CATEGORY_LIST_ALL_OF_SHOP = SERVICE_PREFIX + "category_list_all_of_shop:shopId:";

    /**
     * 用户端分类列表缓存key
     */
    String CATEGORY_LIST = SERVICE_PREFIX + "category_list:";

    /**
     * 购物车商品数量
     */
    String SHOP_CART_ITEM_COUNT = SERVICE_PREFIX + "shop_cart:count:";

    /**
     * sku列表信息缓存key
     */
    String SKU_LIST_KEY = SERVICE_PREFIX + "sku_list:";

    /**
     * 商品详情sku列表信息缓存key
     */
    String SKU_OF_SPU_DETAIL_KEY = SERVICE_PREFIX + "sku_of_spu_detail_list:";

    /**
     * 根据skuid获取sku的缓存key
     */
    String SKU_BY_ID_KEY = SERVICE_PREFIX + "sku:by_id:";

    /**
     * spu信息缓存key
     */
    String SPU_KEY = SERVICE_PREFIX + "spu:";

    /**
     * spu扩展信息缓存key
     */
    String SPU_EXTENSION_KEY = SERVICE_PREFIX + "spu_extension:";
}
