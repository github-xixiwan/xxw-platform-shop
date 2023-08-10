package com.xxw.shop.constant;

import com.xxw.shop.module.common.exception.ErrorEnumInterface;

/**
 * ORDER业务级抛错
 */
public enum OrderBusinessError implements ErrorEnumInterface {

    ORDER_00001("ORDER_00001", "请填写收货地址"),
    ;

    private String code;

    private String message;

    OrderBusinessError(String code, String message) {
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
