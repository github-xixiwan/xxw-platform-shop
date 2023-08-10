package com.xxw.shop.constant;

import com.xxw.shop.module.common.exception.ErrorEnumInterface;

/**
 * PAYMENT业务级抛错
 */
public enum PaymentBusinessError implements ErrorEnumInterface {

    PAYMENT_00001("PAYMENT_00001", "请填写收货地址"),
    ;

    private String code;

    private String message;

    PaymentBusinessError(String code, String message) {
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
