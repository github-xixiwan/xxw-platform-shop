package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 用于登陆传递账号密码
 */
@Data
public class AuthenticationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank(message = "principal不能为空")
    @Schema(description = "用户名", required = true)
    protected String principal;

    /**
     * 密码
     */
    @NotBlank(message = "credentials不能为空")
    @Schema(description = "一般用作密码", required = true)
    protected String credentials;

    /**
     * sysType 参考SysTypeEnum
     */
    @NotNull(message = "sysType不能为空")
    @Schema(description = "系统类型 0.普通用户系统 1.商家端", required = true)
    protected Integer sysType;
}
