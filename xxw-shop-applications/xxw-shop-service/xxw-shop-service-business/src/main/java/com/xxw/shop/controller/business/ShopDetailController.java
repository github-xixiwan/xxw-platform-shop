package com.xxw.shop.controller.business;

import com.xxw.shop.api.business.vo.ShopDetailVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.ShopDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/m/shop_detail")
@RestController("businessShopDetailController")
@Tag(name = "business-店铺详情信息")
public class ShopDetailController {

    @Resource
    private ShopDetailService shopDetailService;

    @GetMapping("/info")
    @Operation(summary = "获取店铺详情信息", description = "获取店铺详情信息")
    public ServerResponseEntity<ShopDetailVO> info() {
        Long shopId = AuthUserContext.get().getTenantId();
        ShopDetailVO shopDetailVO = shopDetailService.getByShopId(shopId);
        return ServerResponseEntity.success(shopDetailVO);
    }
}
