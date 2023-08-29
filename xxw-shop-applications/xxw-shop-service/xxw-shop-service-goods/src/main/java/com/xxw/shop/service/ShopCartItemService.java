package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.ChangeShopCartItemDTO;
import com.xxw.shop.dto.CheckShopCartItemDTO;
import com.xxw.shop.entity.ShopCartItem;
import com.xxw.shop.api.goods.vo.ShopCartItemVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface ShopCartItemService extends IService<ShopCartItem> {

    void deleteShopCartItemsByShopCartItemIds(Long userId, List<Long> shopCartItemIds);

    void addShopCartItem(Long userId, ChangeShopCartItemDTO param, Long priceFee);

    void updateShopCartItem(Long userId, ShopCartItem shopCartItem);

    void deleteAllShopCartItems(Long userId);

    List<ShopCartItemVO> getShopCartItems();

    List<ShopCartItemVO> getShopCartExpiryItems();

    Integer getShopCartItemCount(Long userId);

    List<ShopCartItemVO> getCheckedShopCartItems();

    void removeShopCartItemCache(Long spuId);

    void checkShopCartItems(Long userId, List<CheckShopCartItemDTO> params);
}
