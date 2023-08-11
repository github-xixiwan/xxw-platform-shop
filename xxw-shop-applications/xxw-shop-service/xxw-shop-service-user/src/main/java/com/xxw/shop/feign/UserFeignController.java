package com.xxw.shop.feign;

import com.xxw.shop.api.user.feign.UserFeignClient;
import com.xxw.shop.api.user.vo.UserCompleteVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户地址feign连接
 */
@RestController
public class UserFeignController implements UserFeignClient {

    @Resource
    private UserService userService;

    @Override
    public ServerResponseEntity<List<UserCompleteVO>> getUserByUserIds(List<Long> userIds) {
        List<UserCompleteVO> userList = userService.getUserByUserIds(userIds);
        return ServerResponseEntity.success(userList);
    }

    @Override
    public ServerResponseEntity<UserCompleteVO> getUserData(Long userId) {
        UserCompleteVO user = userService.getByUserId(userId);
        return ServerResponseEntity.success(user);
    }

}
