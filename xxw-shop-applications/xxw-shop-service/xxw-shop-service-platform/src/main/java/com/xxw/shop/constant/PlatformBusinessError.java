package com.xxw.shop.constant;

import com.xxw.shop.module.common.exception.ErrorEnumInterface;

/**
 * PLATFORM业务级抛错
 */
public enum PlatformBusinessError implements ErrorEnumInterface {

    PLATFORM_00001("PLATFORM_00001", "无法获取账户信息"),
    PLATFORM_00002("PLATFORM_00002", "已有账号，无需重复添加");

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
