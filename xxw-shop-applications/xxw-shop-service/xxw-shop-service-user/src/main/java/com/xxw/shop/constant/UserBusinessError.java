package com.xxw.shop.constant;

import com.xxw.shop.module.common.exception.ErrorEnumInterface;

/**
 * USER业务级抛错
 */
public enum UserBusinessError implements ErrorEnumInterface {

    USER_00001("USER_00001", "请先删除子地区"),
    USER_00002("USER_00002", "用户名已存在"),
    USER_00003("USER_00003", "收货地址已达到上限，无法再新增地址"),
    USER_00004("USER_00004", "该地址已被删除"),
    USER_00005("USER_00005", "默认地址不能删除"),
    ;

    private String code;

    private String message;

    UserBusinessError(String code, String message) {
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
