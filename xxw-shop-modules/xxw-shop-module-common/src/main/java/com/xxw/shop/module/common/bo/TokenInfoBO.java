package com.xxw.shop.module.common.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenInfoBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 保存在token信息里面的用户信息
     */
    private UserInfoInTokenBO userInfoInToken;

    private String accessToken;

    private String refreshToken;

    /**
     * 在多少秒后过期
     */
    private Integer expiresIn;
}
