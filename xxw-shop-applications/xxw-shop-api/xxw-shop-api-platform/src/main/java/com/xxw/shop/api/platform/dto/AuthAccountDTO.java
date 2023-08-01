package com.xxw.shop.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthAccountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank(message = "username not blank")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建ip
     */
    private String createIp;

    /**
     * 状态 1:启用 0:禁用 -1:删除
     */
    @NotNull(message = "status not null")
    private Integer status;

    /**
     * 系统类型见SysTypeEnum 0.普通用户系统 1.商家端
     */
    @NotNull(message = "sysType not null")
    private Integer sysType;

    /**
     * 用户id
     */
    @NotNull(message = "userId not null")
    private Long userId;

    /**
     * 所属租户
     */
    @NotNull(message = "tenantId not null")
    private Long tenantId;

    /**
     * 是否是管理员
     */
    @NotNull(message = "isAdmin not null")
    private Integer isAdmin;
}
