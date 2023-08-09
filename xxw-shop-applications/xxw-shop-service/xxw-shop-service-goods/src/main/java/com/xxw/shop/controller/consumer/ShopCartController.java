package com.xxw.shop.controller.consumer;

import cn.hutool.core.collection.CollectionUtil;
import com.xxw.shop.api.goods.vo.ShopCartItemVO;
import com.xxw.shop.api.goods.vo.SkuVO;
import com.xxw.shop.api.goods.vo.SpuVO;
import com.xxw.shop.dto.ChangeShopCartItemDTO;
import com.xxw.shop.dto.CheckShopCartItemDTO;
import com.xxw.shop.entity.ShopCartItem;
import com.xxw.shop.manager.ShopCartAdapter;
import com.xxw.shop.module.common.constant.StatusEnum;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.ShopCartItemService;
import com.xxw.shop.service.SkuService;
import com.xxw.shop.service.SpuService;
import com.xxw.shop.vo.ShopCartAmountVO;
import com.xxw.shop.vo.ShopCartVO;
import com.xxw.shop.vo.ShopCartWithAmountVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 购物车
 */
@RestController
@RequestMapping("/a/shop_cart")
@Tag(name = "app-购物车")
public class ShopCartController {

    @Resource
    private ShopCartItemService shopCartItemService;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private SpuService spuService;

    @Resource
    private SkuService skuService;

    @Resource
    private ShopCartAdapter shopCartAdapter;

    /**
     * 获取用户购物车信息
     *
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "获取用户购物车信息", description = "获取用户购物车信息")
    public ServerResponseEntity<ShopCartWithAmountVO> info() {
        // 拿到购物车的所有item
        List<ShopCartItemVO> shopCartItems = shopCartItemService.getShopCartItems();
        List<ShopCartVO> shopCarts = shopCartAdapter.conversionShopCart(shopCartItems);
        ShopCartWithAmountVO shopCartWithAmountVO = new ShopCartWithAmountVO();
        long total = 0L;
        for (ShopCartItemVO shopCartItem : shopCartItems) {
            if (Objects.equals(shopCartItem.getIsChecked(), 1)) {
                total += shopCartItem.getTotalAmount();
            }
        }
        shopCartWithAmountVO.setShopCarts(shopCarts);
        shopCartWithAmountVO.setTotalMoney(total);
        return ServerResponseEntity.success(shopCartWithAmountVO);
    }

    /**
     * 获取用户购物车信息
     *
     * @return
     */
    @GetMapping("/amount_info")
    @Operation(summary = "获取用户购物车金额信息", description = "获取用户购物车金额信息")
    public ServerResponseEntity<ShopCartAmountVO> amountInfo() {
        // 拿到购物车的所有item
        List<ShopCartItemVO> shopCartItems = shopCartItemService.getShopCartItems();
        List<ShopCartVO> shopCarts = shopCartAdapter.conversionShopCart(shopCartItems);
        ShopCartWithAmountVO shopCartWithAmountVO = new ShopCartWithAmountVO();
        shopCartWithAmountVO.setShopCarts(shopCarts);

        return ServerResponseEntity.success(mapperFacade.map(shopCartWithAmountVO, ShopCartAmountVO.class));
    }


    @DeleteMapping("/delete_item")
    @Operation(summary = "删除用户购物车物品", description = "通过购物车id删除用户购物车物品")
    public ServerResponseEntity<Void> deleteItem(@RequestBody List<Long> shopCartItemIds) {
        Long userId = AuthUserContext.get().getUserId();
        shopCartItemService.deleteShopCartItemsByShopCartItemIds(userId, shopCartItemIds);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delete_all")
    @Operation(summary = "清空用户购物车所有物品", description = "清空用户购物车所有物品")
    public ServerResponseEntity<String> deleteAll() {
        Long userId = AuthUserContext.get().getUserId();
        shopCartItemService.deleteAllShopCartItems(userId);
        // 删除成功
        return ServerResponseEntity.success();
    }

    @PostMapping("/check_items")
    @Operation(summary = "", description = "")
    public ServerResponseEntity<Void> checkItems(@Valid @RequestBody List<CheckShopCartItemDTO> params) {
        if (CollectionUtil.isEmpty(params)) {
            return ServerResponseEntity.success();
        }
        Long userId = AuthUserContext.get().getUserId();
        shopCartItemService.checkShopCartItems(userId, params);
        return ServerResponseEntity.success();
    }


    @PostMapping("/change_item")
    @Operation(summary = "添加、修改用户购物车物品", description = "通过商品id(prodId)、skuId、店铺Id(shopId),添加/修改用户购物车商品，并传入改变的商品个数" +
            "(count)，" + "当count为正值时，增加商品数量，当count为负值时，将减去商品的数量，当最终count值小于0时，会将商品从购物车里面删除")
    public ServerResponseEntity<Void> addItem(@Valid @RequestBody ChangeShopCartItemDTO param) {

        // 不用校验库存是否充足！！！
        Long userId = AuthUserContext.get().getUserId();
        List<ShopCartItemVO> shopCartItems = shopCartItemService.getShopCartItems();

        SpuVO spu = spuService.getBySpuId(param.getSpuId());
        SkuVO sku = skuService.getSkuBySkuId(param.getSkuId());


        // 当商品状态不正常时，不能添加到购物车
        if (Objects.isNull(spu) || Objects.isNull(sku) || !Objects.equals(spu.getStatus(), StatusEnum.ENABLE.value()) || !Objects.equals(sku.getStatus(), StatusEnum.ENABLE.value()) || !Objects.equals(sku.getSpuId(), spu.getSpuId())) {
            // 当返回商品不存在时，前端应该将商品从购物车界面移除
            return ServerResponseEntity.fail(SystemErrorEnumError.SPU_NOT_EXIST);
        }
        // 保存shopId，不要让前端传过来
        param.setShopId(spu.getShopId());


        Integer oldCount = 0;
        Long oldShopCartItemId = null;
        for (ShopCartItemVO shopCartItemVo : shopCartItems) {
            if (Objects.equals(param.getSkuId(), shopCartItemVo.getSkuId())) {
                // 旧数量
                oldCount = shopCartItemVo.getCount();
                oldShopCartItemId = shopCartItemVo.getCartItemId();
                ShopCartItem shopCartItem = new ShopCartItem();
                shopCartItem.setUserId(userId);
                shopCartItem.setCount(param.getCount() + shopCartItemVo.getCount());
                shopCartItem.setIsChecked(shopCartItemVo.getIsChecked());
                shopCartItem.setCartItemId(shopCartItemVo.getCartItemId());
                // 如果有个旧的sku，就说明是在切换sku
                if (Objects.nonNull(param.getOldSkuId())) {
                    continue;
                }
                // 防止购物车变成负数，从购物车删除
                if (shopCartItem.getCount() <= 0) {
                    shopCartItemService.deleteShopCartItemsByShopCartItemIds(userId,
                            Collections.singletonList(shopCartItem.getCartItemId()));
                    return ServerResponseEntity.success();
                }
                shopCartItemService.updateShopCartItem(userId, shopCartItem);
                return ServerResponseEntity.success();
            }
        }

        if (Objects.nonNull(param.getOldSkuId())) {
            for (ShopCartItemVO oldShopCartItem : shopCartItems) {
                // 旧sku
                if (Objects.equals(param.getOldSkuId(), oldShopCartItem.getSkuId())) {
                    ShopCartItem shopCartItem = new ShopCartItem();
                    shopCartItem.setUserId(userId);
                    shopCartItem.setCartItemId(oldShopCartItem.getCartItemId());
                    // 如果以前就存在这个商品，还要把以前的商品数量累加
                    shopCartItem.setCount(param.getCount() + oldCount);
                    shopCartItem.setSkuId(param.getSkuId());

                    if (oldShopCartItemId != null) {
                        // 删除旧的购物项
                        shopCartItemService.deleteShopCartItemsByShopCartItemIds(userId,
                                Collections.singletonList(oldShopCartItemId));
                    }
                    // 更新购物车
                    shopCartItemService.updateShopCartItem(userId, shopCartItem);
                    return ServerResponseEntity.success();
                }
            }
        }

        // 所有都正常时
        shopCartItemService.addShopCartItem(userId, param, sku.getPriceFee());
        // 添加成功
        return ServerResponseEntity.success();
    }

    @GetMapping("/prod_count")
    @Operation(summary = "获取购物车商品数量", description = "获取购物车商品数量")
    public ServerResponseEntity<Integer> prodCount() {
        return ServerResponseEntity.success(shopCartItemService.getShopCartItemCount(AuthUserContext.get().getUserId()));
    }

    @GetMapping("/expiry_prod_list")
    @Operation(summary = "获取购物车失效商品信息", description = "获取购物车失效商品列表")
    public ServerResponseEntity<List<ShopCartItemVO>> expiryProdList() {
        return ServerResponseEntity.success(shopCartItemService.getShopCartExpiryItems());
    }

}
