package com.xxw.shop.controller;

import com.xxw.shop.api.auth.vo.TokenInfoVO;
import com.xxw.shop.constant.AuthBusinessError;
import com.xxw.shop.dto.UpdatePasswordDTO;
import com.xxw.shop.entity.AuthAccount;
import com.xxw.shop.manager.TokenStore;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.security.bo.UserInfoInTokenBO;
import com.xxw.shop.service.AuthAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "密码")
public class PasswordController {

    @Resource
    private TokenStore tokenStore;

    @Resource
    private AuthAccountService authAccountService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @PutMapping("/update_password")
    @Operation(summary = "更新密码", description = "更新当前用户的密码, 更新密码之后要退出登录，清理token")
    public ServerResponseEntity<TokenInfoVO> updatePassword(@Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        UserInfoInTokenBO userInfoInToken = AuthUserContext.get();
        AuthAccount authAccount = authAccountService.getByUserIdAndType(userInfoInToken.getUserId(),
                userInfoInToken.getSysType());
        if (!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), authAccount.getPassword())) {
            return ServerResponseEntity.fail(AuthBusinessError.AUTH_00007);
        }
        authAccountService.modifyPassword(userInfoInToken.getUserId(), userInfoInToken.getSysType(),
                updatePasswordDTO.getNewPassword());
        tokenStore.deleteAllToken(userInfoInToken.getSysType().toString(), userInfoInToken.getUid());
        return ServerResponseEntity.success();
    }


}
