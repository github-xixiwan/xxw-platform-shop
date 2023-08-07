package com.xxw.shop.module.role.vo;

import com.mybatisflex.annotation.Column;
import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色id")
    private Long roleId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建者ID")
    private Long createUserId;

    @Schema(description = "所属租户id")
    private Long tenantId;

    @Schema(description = "类型")
    private Integer bizType;

    @Column(ignore = true)
    @Schema(description = "菜单id列表")
    private List<Long> menuIds;

    @Column(ignore = true)
    @Schema(description = "菜单资源id列表")
    private List<Long> menuPermissionIds;
}
