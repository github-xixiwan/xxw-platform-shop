package com.xxw.shop.api.goods.feign;

import com.xxw.shop.api.goods.vo.ShopCartItemVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.feign.FeignInsideAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "shop-goods", contextId = "shopCartFeign")
public interface ShopCartFeignClient {

    /**
     * 获取购物项
     *
     * @return 购物项
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/shopCart/getById")
    ServerResponseEntity<List<ShopCartItemVO>> getCheckedShopCartItems();

    /**
     * 通过购物车id删除用户购物车物品
     *
     * @param shopCartItemIds 购物车id
     * @return
     */
    @DeleteMapping("/delete_item")
    ServerResponseEntity<Void> deleteItem(@RequestBody List<Long> shopCartItemIds);
}
