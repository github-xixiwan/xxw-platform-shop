package com.xxw.shop.api.rbac.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @NotNull(message = "userId not null")
    private Long userId;


    /**
     * 角色id列表
     */
    @NotEmpty(message = "userId not null")
    private List<Long> roleIds;
}
