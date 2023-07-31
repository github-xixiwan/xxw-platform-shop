package com.xxw.shop.module.menu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuPermissionSimpleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单资源用户id")
    private Long menuPermissionId;

    @Schema(description = "资源关联菜单")
    private Long menuId;

    @Schema(description = "资源名称")
    private String name;
}
