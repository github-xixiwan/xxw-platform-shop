package com.xxw.shop.module.common.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthAccountInVerifyBO extends UserInfoInTokenBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String password;

    private Integer status;

}
