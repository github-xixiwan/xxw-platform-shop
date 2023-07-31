package com.xxw.shop.module.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色id")
    private Long roleId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "菜单id列表")
    private List<Long> menuIds;

    @Schema(description = "菜单资源id列表")
    private List<Long> menuPermissionIds;
}
