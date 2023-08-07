package com.xxw.shop.constant;

import com.xxw.shop.module.common.exception.ErrorEnumInterface;

/**
 * SUPPORT业务级抛错
 */
public enum SupportBusinessError implements ErrorEnumInterface {

    SUPPORT_00001("SUPPORT_00001", "图片名称不能为空");

    private String code;

    private String message;

    SupportBusinessError(String code, String message) {
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
