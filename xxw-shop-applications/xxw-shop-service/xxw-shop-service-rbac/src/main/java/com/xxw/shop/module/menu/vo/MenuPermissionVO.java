package com.xxw.shop.module.menu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuPermissionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单资源用户id")
    private Long menuPermissionId;

    @Schema(description = "资源关联菜单")
    private Long menuId;

    @Schema(description = "菜单标题")
    private String menuTitle;

    @Schema(description = "权限对应的编码")
    private String permission;

    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "资源对应服务器路径")
    private String uri;

    @Schema(description = "请求方法 1.GET 2.POST 3.PUT 4.DELETE")
    private Integer method;
}
