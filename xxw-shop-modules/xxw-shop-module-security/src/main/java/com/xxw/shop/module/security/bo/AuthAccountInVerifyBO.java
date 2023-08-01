package com.xxw.shop.module.security.bo;

import java.io.Serializable;

public class AuthAccountInVerifyBO extends UserInfoInTokenBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String password;

    private Integer status;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AuthAccountInVerifyBO{" + "password='" + password + '\'' + ", status=" + status + "} " + super.toString();
    }


}
