package com.xxw.shop.constant;

import com.xxw.shop.module.common.exception.ErrorEnumInterface;

/**
 * SEARCH业务级抛错
 */
public enum SearchBusinessError implements ErrorEnumInterface {

    SEARCH_00001("SEARCH_00001", "搜索服务出了点小差，请稍后再试")
    ;

    private String code;

    private String message;

    SearchBusinessError(String code, String message) {
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
