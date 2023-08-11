package com.xxw.shop.controller.consumer;

import cn.hutool.core.util.StrUtil;
import com.xxw.shop.api.auth.feign.AccountFeignClient;
import com.xxw.shop.api.auth.vo.TokenInfoVO;
import com.xxw.shop.dto.UserRegisterDTO;
import com.xxw.shop.module.common.bo.UserInfoInTokenBO;
import com.xxw.shop.module.common.constant.SysTypeEnum;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ua/user/register")
@Tag(name = "consumer-用户注册接口")
public class UserRegisterController {

    @Resource
    private UserService userService;

    @Resource
    private AccountFeignClient accountFeignClient;

    @Operation(summary = "注册")
    @PostMapping
    public ServerResponseEntity<TokenInfoVO> register(@Valid @RequestBody UserRegisterDTO param) {
        if (StrUtil.isBlank(param.getNickName())) {
            param.setNickName(param.getUserName());
        }
        // 1. 保存账户信息
        Long uid = userService.saveUser(param);
        // 2. 登录
        UserInfoInTokenBO userInfoInTokenBO = new UserInfoInTokenBO();
        userInfoInTokenBO.setUid(uid);
        userInfoInTokenBO.setUserId(param.getUserId());
        userInfoInTokenBO.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInTokenBO.setIsAdmin(0);
        return accountFeignClient.storeTokenAndGetVo(userInfoInTokenBO);
    }

}
