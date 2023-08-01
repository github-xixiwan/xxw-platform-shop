package com.xxw.shop.constant;

import com.xxw.shop.module.util.exception.ErrorEnumInterface;

/**
 * AUTH业务级抛错
 */
public enum AuthBusinessError implements ErrorEnumInterface {

    AUTH_00001("AUTH_00001", "权限编码已存在，请勿重复添加");

    private String code;

    private String message;

    AuthBusinessError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
