package com.xxw.shop.controller.consumer;

import com.xxw.shop.api.user.vo.UserCompleteVO;
import com.xxw.shop.entity.User;
import com.xxw.shop.module.common.bo.UserInfoInTokenBO;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.UserService;
import com.xxw.shop.vo.UserSimpleInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 用户地址
 */
@RestController("consumerUserController")
@RequestMapping("/a/user")
@Tag(name = "consumer-用户信息")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/simple_info")
    @Operation(summary = "用户头像昵称", description = "用户头像昵称")
    public ServerResponseEntity<UserSimpleInfoVO> getByAddrId() {
        Long userId = AuthUserContext.get().getUserId();
        UserCompleteVO userVO = userService.getByUserId(userId);
        UserSimpleInfoVO userSimpleInfoVO = new UserSimpleInfoVO();
        userSimpleInfoVO.setNickName(userVO.getNickName());
        userSimpleInfoVO.setPic(userVO.getPic());
        return ServerResponseEntity.success(userSimpleInfoVO);
    }


    @GetMapping("/ma/user_detail_info")
    @Operation(summary = "获取用户详细信息", description = "返回用户详细信息")
    public ServerResponseEntity<UserCompleteVO> getUserDetailInfo() {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        if (userInfoInTokenBO == null) {
            return ServerResponseEntity.fail(SystemErrorEnumError.CLEAN_TOKEN);
        }
        Long userId = userInfoInTokenBO.getUserId();
        UserCompleteVO userVO = userService.getByUserId(userId);
        return ServerResponseEntity.success(userVO);
    }

    @PostMapping("/ma/update_user")
    @Operation(summary = "更新用户信息")
    public ServerResponseEntity<Void> updateUser(@RequestBody UserCompleteVO userVO) {
        Long userId = AuthUserContext.get().getUserId();
        UserCompleteVO byUserId = userService.getByUserId(userId);
        User user = new User();
        user.setUserId(userId);
        user.setNickName(Objects.isNull(userVO.getNickName()) ? byUserId.getNickName() : userVO.getNickName());
        user.setPic(Objects.isNull(userVO.getPic()) ? byUserId.getPic() : userVO.getPic());
        userService.updateUser(user);
        return ServerResponseEntity.success();
    }
}
