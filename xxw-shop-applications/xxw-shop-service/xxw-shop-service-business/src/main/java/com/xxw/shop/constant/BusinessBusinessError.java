package com.xxw.shop.constant;

import com.xxw.shop.module.common.exception.ErrorEnumInterface;

/**
 * BUSINESS业务级抛错
 */
public enum BusinessBusinessError implements ErrorEnumInterface {

    BUSINESS_00001("BUSINESS_00001", "商家信息获取失败"),
    BUSINESS_00002("BUSINESS_00002", "店铺名称已存在"),
    BUSINESS_00003("BUSINESS_00003", "用户名格式不正确"),
    BUSINESS_00004("BUSINESS_00004", "用户账号已存在"),
    BUSINESS_00005("BUSINESS_00005", "该用户已经创建过店铺"),
    BUSINESS_00006("BUSINESS_00006", "店铺不存在"),
    ;

    private String code;

    private String message;

    BusinessBusinessError(String code, String message) {
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
