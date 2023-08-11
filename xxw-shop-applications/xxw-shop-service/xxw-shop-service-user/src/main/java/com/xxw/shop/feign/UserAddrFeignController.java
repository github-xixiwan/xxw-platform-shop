package com.xxw.shop.feign;

import com.xxw.shop.api.user.feign.UserAddrFeignClient;
import com.xxw.shop.api.user.vo.UserAddrVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.UserAddrService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户地址feign连接
 */
@RestController
public class UserAddrFeignController implements UserAddrFeignClient {

    @Resource
    private UserAddrService userAddrService;

    @Override
    public ServerResponseEntity<UserAddrVO> getUserAddrByAddrId(Long addrId) {
        return ServerResponseEntity.success(userAddrService.getUserAddrByUserId(addrId,
                AuthUserContext.get().getUserId()));
    }
}
