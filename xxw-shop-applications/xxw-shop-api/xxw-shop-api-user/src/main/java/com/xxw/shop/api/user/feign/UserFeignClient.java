package com.xxw.shop.api.user.feign;

import com.xxw.shop.api.user.vo.UserCompleteVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.feign.FeignInsideAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户信息feign连接
 */
@FeignClient(value = "shop-user", contextId = "userFeign")
public interface UserFeignClient {

    /**
     * 根据用户id列表，获取用户信息
     *
     * @param userIds 用户id列表
     * @return 用户列表信息
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/user/getUserByUserIds")
    ServerResponseEntity<List<UserCompleteVO>> getUserByUserIds(@RequestParam("userId") List<Long> userIds);

    /**
     * 获取用户数据
     *
     * @param userId
     * @return
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/user/getUserData")
    ServerResponseEntity<UserCompleteVO> getUserData(Long userId);

}
