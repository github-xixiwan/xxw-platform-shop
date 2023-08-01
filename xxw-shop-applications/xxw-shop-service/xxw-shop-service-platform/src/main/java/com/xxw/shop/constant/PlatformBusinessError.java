package com.xxw.shop.constant;

import com.xxw.shop.module.util.exception.ErrorEnumInterface;

/**
 * PLATFORM业务级抛错
 */
public enum PlatformBusinessError implements ErrorEnumInterface {

    PLATFORM_00001("AUTH_00001", "accessToken is blank");

    private String code;

    private String message;

    PlatformBusinessError(String code, String message) {
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
