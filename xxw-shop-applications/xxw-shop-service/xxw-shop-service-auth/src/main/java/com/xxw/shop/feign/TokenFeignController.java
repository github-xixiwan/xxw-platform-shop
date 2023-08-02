package com.xxw.shop.feign;

import com.xxw.shop.api.auth.feign.TokenFeignClient;
import com.xxw.shop.manager.TokenStore;
import com.xxw.shop.module.web.response.ServerResponseEntity;
import com.xxw.shop.module.web.security.bo.UserInfoInTokenBO;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenFeignController implements TokenFeignClient {

    private static final Logger logger = LoggerFactory.getLogger(TokenFeignController.class);

    @Resource
    private TokenStore tokenStore;

    @Override
    public ServerResponseEntity<UserInfoInTokenBO> checkToken(String accessToken) {
        ServerResponseEntity<UserInfoInTokenBO> userInfoByAccessTokenResponse = tokenStore
                .getUserInfoByAccessToken(accessToken, true);
        if (!userInfoByAccessTokenResponse.isSuccess()) {
            return ServerResponseEntity.transform(userInfoByAccessTokenResponse);
        }
        return ServerResponseEntity.success(userInfoByAccessTokenResponse.getData());
    }

}
