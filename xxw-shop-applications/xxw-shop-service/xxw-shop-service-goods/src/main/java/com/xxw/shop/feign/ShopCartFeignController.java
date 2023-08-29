package com.xxw.shop.feign;

import cn.hutool.core.collection.CollectionUtil;
import com.xxw.shop.api.goods.feign.ShopCartFeignClient;
import com.xxw.shop.api.goods.vo.ShopCartItemVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.ShopCartItemService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopCartFeignController implements ShopCartFeignClient {

    @Resource
    private ShopCartItemService shopCartItemService;

    @Override
    public ServerResponseEntity<List<ShopCartItemVO>> getCheckedShopCartItems() {
        List<ShopCartItemVO> checkedShopCartItems = shopCartItemService.getCheckedShopCartItems();
        if (CollectionUtil.isNotEmpty(checkedShopCartItems)) {
            for (ShopCartItemVO shopCartItem : checkedShopCartItems) {
                shopCartItem.setTotalAmount(shopCartItem.getCount() * shopCartItem.getSkuPriceFee());
            }
        }
        return ServerResponseEntity.success(checkedShopCartItems);
    }

    @Override
    public ServerResponseEntity<Void> deleteItem(List<Long> shopCartItemIds) {
        Long userId = AuthUserContext.get().getUserId();
        shopCartItemService.deleteShopCartItemsByShopCartItemIds(userId, shopCartItemIds);
        return ServerResponseEntity.success();
    }
}
