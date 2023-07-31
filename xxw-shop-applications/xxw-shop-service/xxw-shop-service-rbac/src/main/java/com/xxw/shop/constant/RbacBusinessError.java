package com.xxw.shop.constant;

import com.xxw.shop.module.util.exception.ErrorEnumInterface;

/**
 * RBAC业务级抛错
 */
public enum RbacBusinessError implements ErrorEnumInterface {

    ORDER_NOT_FOUND_EXCEPTION(409001, "订单未找到");

    private String code;

    private String msg;

    RbacBusinessError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
