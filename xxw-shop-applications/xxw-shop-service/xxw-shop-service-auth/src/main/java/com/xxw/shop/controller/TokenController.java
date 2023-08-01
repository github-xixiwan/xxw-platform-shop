package com.xxw.shop.controller;

import com.xxw.shop.api.auth.vo.TokenInfoVO;
import com.xxw.shop.dto.RefreshTokenDTO;
import com.xxw.shop.manager.TokenStore;
import com.xxw.shop.module.web.response.ServerResponseEntity;
import com.xxw.shop.module.web.security.bo.TokenInfoBO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "token")
public class TokenController {

    @Resource
    private TokenStore tokenStore;

    @Resource
    private MapperFacade mapperFacade;

    @PostMapping("/ua/token/refresh")
    public ServerResponseEntity<TokenInfoVO> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        ServerResponseEntity<TokenInfoBO> tokenInfoServerResponseEntity = tokenStore.refreshToken(refreshTokenDTO.getRefreshToken());
        if (!tokenInfoServerResponseEntity.isSuccess()) {
            return ServerResponseEntity.transform(tokenInfoServerResponseEntity);
        }
        return ServerResponseEntity.success(mapperFacade.map(tokenInfoServerResponseEntity.getData(), TokenInfoVO.class));
    }

}
