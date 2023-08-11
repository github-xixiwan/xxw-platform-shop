package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.cache.GoodsCacheNames;
import com.xxw.shop.dto.ChangeShopCartItemDTO;
import com.xxw.shop.dto.CheckShopCartItemDTO;
import com.xxw.shop.entity.ShopCartItem;
import com.xxw.shop.mapper.ShopCartItemMapper;
import com.xxw.shop.module.cache.tool.IGlobalRedisCacheManager;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.ShopCartItemService;
import com.xxw.shop.module.common.vo.ShopCartItemVO;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.xxw.shop.entity.table.ShopCartItemTableDef.SHOP_CART_ITEM;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class ShopCartItemServiceImpl extends ServiceImpl<ShopCartItemMapper, ShopCartItem> implements ShopCartItemService {

    @Resource
    private IGlobalRedisCacheManager globalRedisCacheManager;

    @Override
    @CacheEvict(cacheNames = GoodsCacheNames.SHOP_CART_ITEM_COUNT, key = "#userId")
    public void deleteShopCartItemsByShopCartItemIds(Long userId, List<Long> shopCartItemIds) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_CART_ITEM.USER_ID.eq(userId));
        queryWrapper.and(SHOP_CART_ITEM.CART_ITEM_ID.in(shopCartItemIds));
        this.remove(queryWrapper);
    }

    @Override
    @CacheEvict(cacheNames = GoodsCacheNames.SHOP_CART_ITEM_COUNT, key = "#userId")
    public void addShopCartItem(Long userId, ChangeShopCartItemDTO param, Long priceFee) {
        ShopCartItem shopCartItem = new ShopCartItem();
        shopCartItem.setCount(param.getCount());
        shopCartItem.setSpuId(param.getSpuId());
        shopCartItem.setShopId(param.getShopId());
        shopCartItem.setUserId(userId);
        shopCartItem.setSkuId(param.getSkuId());
        shopCartItem.setIsChecked(1);
        shopCartItem.setPriceFee(priceFee);
        this.save(shopCartItem);
    }

    @Override
    @CacheEvict(cacheNames = GoodsCacheNames.SHOP_CART_ITEM_COUNT, key = "#userId")
    public void updateShopCartItem(Long userId, ShopCartItem shopCartItem) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_CART_ITEM.USER_ID.eq(userId));
        queryWrapper.and(SHOP_CART_ITEM.CART_ITEM_ID.in(shopCartItem.getCartItemId()));
        this.update(shopCartItem, queryWrapper);
    }

    @Override
    @CacheEvict(cacheNames = GoodsCacheNames.SHOP_CART_ITEM_COUNT, key = "#userId")
    public void deleteAllShopCartItems(Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_CART_ITEM.USER_ID.eq(userId));
        this.remove(queryWrapper);
    }

    @Override
    public List<ShopCartItemVO> getShopCartItems() {
        Long userId = AuthUserContext.get().getUserId();
        List<ShopCartItemVO> shopCartItems = mapper.getShopCartItems(userId, false, null);
        for (ShopCartItemVO shopCartItem : shopCartItems) {
            shopCartItem.setTotalAmount(shopCartItem.getCount() * shopCartItem.getSkuPriceFee());
        }
        return shopCartItems;
    }

    @Override
    public List<ShopCartItemVO> getShopCartExpiryItems() {
        Long userId = AuthUserContext.get().getUserId();
        List<ShopCartItemVO> shopCartItems = mapper.getShopCartItems(userId, true, null);
        for (ShopCartItemVO shopCartItem : shopCartItems) {
            shopCartItem.setTotalAmount(shopCartItem.getCount() * shopCartItem.getSkuPriceFee());
        }
        return shopCartItems;
    }

    @Override
    @Cacheable(cacheNames = GoodsCacheNames.SHOP_CART_ITEM_COUNT, key = "#userId")
    public Integer getShopCartItemCount(Long userId) {
        return mapper.getShopCartItemCount(userId);
    }

    @Override
    public List<ShopCartItemVO> getCheckedShopCartItems() {
        Long userId = AuthUserContext.get().getUserId();
        return mapper.getShopCartItems(userId, false, true);
    }

    @Override
    public void removeShopCartItemCache(Long spuId) {
        List<String> userIds = mapper.listUserIdBySpuId(spuId);
        if (CollectionUtil.isEmpty(userIds)) {
            return;
        }
        for (String userId : userIds) {
            globalRedisCacheManager.evictCache(GoodsCacheNames.SHOP_CART_ITEM_COUNT, userId);
        }
    }

    @Override
    public void checkShopCartItems(Long userId, List<CheckShopCartItemDTO> params) {
        mapper.checkShopCartItems(userId, params);
    }
}
