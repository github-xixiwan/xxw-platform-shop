package com.xxw.shop.constant;

import com.xxw.shop.module.util.exception.ErrorEnumInterface;

/**
 * RBAC业务级抛错
 */
public enum RbacBusinessError implements ErrorEnumInterface {

    RBAC_00001("RBAC_00001", "权限编码已存在，请勿重复添加"),
    RBAC_00002("RBAC_00002", "无权限操作");

    private String code;

    private String message;

    RbacBusinessError(String code, String message) {
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
