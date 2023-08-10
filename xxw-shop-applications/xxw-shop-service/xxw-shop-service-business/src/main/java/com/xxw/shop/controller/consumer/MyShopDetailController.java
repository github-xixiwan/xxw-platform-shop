package com.xxw.shop.controller.consumer;

import com.xxw.shop.api.business.vo.ShopDetailVO;
import com.xxw.shop.dto.ShopDetailDTO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.ShopDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping(value = "/my_shop_detail")
@RestController("consumerMyShopDetailController")
@Tag(name = "consumer-我的店铺详情信息")
public class MyShopDetailController {

    @Resource
    private ShopDetailService shopDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建店铺", description = "创建店铺")
    public ServerResponseEntity<Void> create(@Valid @RequestBody ShopDetailDTO dto) {
        shopDetailService.createShop(dto);
        return ServerResponseEntity.success();
    }

    @GetMapping
    @Operation(summary = "获取我的店铺", description = "获取我的店铺")
    public ServerResponseEntity<ShopDetailVO> get() {
        Long shopId = AuthUserContext.get().getTenantId();
        if (Objects.isNull(shopId)) {
            return ServerResponseEntity.success(null);
        }
        return ServerResponseEntity.success(shopDetailService.getByShopId(shopId));
    }
}
