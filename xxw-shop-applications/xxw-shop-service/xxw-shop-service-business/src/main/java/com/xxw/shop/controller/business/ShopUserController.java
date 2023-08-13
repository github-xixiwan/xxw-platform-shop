package com.xxw.shop.controller.business;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.dto.ShopUserDTO;
import com.xxw.shop.dto.ShopUserQueryDTO;
import com.xxw.shop.entity.ShopUser;
import com.xxw.shop.module.common.bo.UserInfoInTokenBO;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.ShopUserService;
import com.xxw.shop.vo.ShopUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping(value = "/b/shop_user")
@RestController("businessShopUserController")
@Tag(name = "店铺用户信息")
public class ShopUserController {

    @Resource
    private ShopUserService shopUserService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/page")
    @Operation(summary = "店铺用户列表", description = "获取店铺用户列表")
    public ServerResponseEntity<Page<ShopUserVO>> page(@Valid ShopUserQueryDTO dto) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        dto.setShopId(userInfoInTokenBO.getTenantId());
        return ServerResponseEntity.success(shopUserService.page(dto));
    }

    @GetMapping("/info")
    @Operation(summary = "登陆店铺用户信息", description = "获取当前登陆店铺用户的用户信息")
    public ServerResponseEntity<ShopUserVO> info() {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        ShopUserVO shopUserVO = shopUserService.getByUserId(userInfoInTokenBO.getUserId());
        shopUserVO.setIsAdmin(userInfoInTokenBO.getIsAdmin());
        return ServerResponseEntity.success(shopUserVO);
    }

    @GetMapping
    @Operation(summary = "获取店铺用户信息", description = "根据用户id获取店铺用户信息")
    public ServerResponseEntity<ShopUserVO> detail(@RequestParam Long shopUserId) {
        return ServerResponseEntity.success(shopUserService.getByUserId(shopUserId));
    }

    @PostMapping
    @Operation(summary = "保存店铺用户信息", description = "保存店铺用户信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody ShopUserDTO dto) {
        ShopUser shopUser = mapperFacade.map(dto, ShopUser.class);
        //TODO
        shopUser.setShopUserId(System.currentTimeMillis());
        shopUser.setShopId(AuthUserContext.get().getTenantId());
        shopUser.setHasAccount(0);
        shopUserService.saveShopUser(shopUser, dto.getRoleIds());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新店铺用户信息", description = "更新店铺用户信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody ShopUserDTO dto) {
        ShopUser shopUser = mapperFacade.map(dto, ShopUser.class);
        ShopUserVO dbShopUser = shopUserService.getByUserId(dto.getShopUserId());
        if (!Objects.equals(dbShopUser.getShopId(), AuthUserContext.get().getTenantId())) {
            return ServerResponseEntity.fail(SystemErrorEnumError.UNAUTHORIZED);
        }
        shopUser.setShopId(dbShopUser.getShopId());
        shopUserService.updateShopUser(shopUser, dto.getRoleIds());
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除店铺用户信息", description = "根据店铺用户id删除店铺用户信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long shopUserId) {
        ShopUserVO dbShopUser = shopUserService.getByUserId(shopUserId);
        if (!Objects.equals(dbShopUser.getShopId(), AuthUserContext.get().getTenantId())) {
            return ServerResponseEntity.fail(SystemErrorEnumError.UNAUTHORIZED);
        }
        shopUserService.deleteById(shopUserId);
        return ServerResponseEntity.success();
    }
}
