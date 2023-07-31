package com.xxw.shop.module.menu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuSimpleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单id")
    private Long menuId;

    @Schema(description = "父菜单ID，一级菜单为0")
    private Long parentId;

    @Schema(description = "设置该路由在侧边栏和面包屑中展示的名字")
    private String title;

    @Schema(description = "菜单权限列表")
    private List<MenuPermissionSimpleVO> menuPermissions;
}
