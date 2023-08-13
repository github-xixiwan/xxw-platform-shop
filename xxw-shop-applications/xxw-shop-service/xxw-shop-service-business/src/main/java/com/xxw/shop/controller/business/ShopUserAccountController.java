package com.xxw.shop.controller.business;

import com.xxw.shop.api.auth.vo.AuthAccountVO;
import com.xxw.shop.constant.BusinessBusinessError;
import com.xxw.shop.dto.ChangeAccountDTO;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.ShopUserService;
import com.xxw.shop.vo.ShopUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping(value = "/b/shop_user/account")
@RestController
@Tag(name = "店铺用户账号信息")
public class ShopUserAccountController {

    @Resource
    private ShopUserService shopUserService;

    @GetMapping
    @Operation(summary = "获取账号信息", description = "获取账号信息")
    public ServerResponseEntity<AuthAccountVO> getAccount(Long shopUserId) {
        return shopUserService.getByUserIdAndSysType(shopUserId, AuthUserContext.get().getSysType());
    }

    @PostMapping
    @Operation(summary = "添加账号", description = "添加账号")
    public ServerResponseEntity<Void> addAccount(@Valid @RequestBody ChangeAccountDTO changeAccountDTO) {
        ShopUserVO shopUserVO = shopUserService.getByUserId(changeAccountDTO.getUserId());
        if (shopUserVO == null) {
            return ServerResponseEntity.fail(BusinessBusinessError.BUSINESS_00007);
        }
        if (Objects.equals(shopUserVO.getHasAccount(), 1)) {
            return ServerResponseEntity.fail(BusinessBusinessError.BUSINESS_00008);
        }
        if (!Objects.equals(shopUserVO.getShopId(), AuthUserContext.get().getTenantId())) {
            return ServerResponseEntity.fail(SystemErrorEnumError.UNAUTHORIZED);
        }
        return shopUserService.saveChangeAccount(changeAccountDTO);
    }

    @PutMapping
    @Operation(summary = "修改账号", description = "修改账号")
    public ServerResponseEntity<Void> updateAccount(@Valid @RequestBody ChangeAccountDTO changeAccountDTO) {
        ShopUserVO shopUserVO = shopUserService.getByUserId(changeAccountDTO.getUserId());
        if (shopUserVO == null || Objects.equals(shopUserVO.getHasAccount(), 0)) {
            return ServerResponseEntity.fail(BusinessBusinessError.BUSINESS_00007);
        }
        if (!Objects.equals(shopUserVO.getShopId(), AuthUserContext.get().getTenantId())) {
            return ServerResponseEntity.fail(SystemErrorEnumError.UNAUTHORIZED);
        }
        return shopUserService.updateChangeAccount(changeAccountDTO);
    }
}
