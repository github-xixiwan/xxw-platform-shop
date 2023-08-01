package com.xxw.shop.constant;

import com.xxw.shop.module.util.exception.ErrorEnumInterface;

/**
 * AUTH业务级抛错
 */
public enum AuthBusinessError implements ErrorEnumInterface {

    AUTH_00001("AUTH_00001", "accessToken is blank"),
    AUTH_00002("AUTH_00002", "accessToken 已过期"),
    AUTH_00003("AUTH_00003", "token 格式有误"),
    AUTH_00004("AUTH_00004", "用户名格式不正确"),
    AUTH_00005("AUTH_00005", "用户名已存在，请更换用户名再次尝试"),
    AUTH_00006("AUTH_00006", "用户信息错误，更新失败"),
    AUTH_00007("AUTH_00007", "旧密码不正确"),
    AUTH_00008("AUTH_00008", "用户名不能为空"),
    AUTH_00009("AUTH_00009", "密码不能为空"),
    AUTH_00010("AUTH_00010", "请输入正确的用户名"),
    AUTH_00011("AUTH_00011", "用户名或密码不正确"),
    AUTH_00012("AUTH_00012", "用户已禁用，请联系客服"),
    ;

    private String code;

    private String message;

    AuthBusinessError(String code, String message) {
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
