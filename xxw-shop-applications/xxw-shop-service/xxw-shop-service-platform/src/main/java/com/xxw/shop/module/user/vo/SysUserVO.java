package com.xxw.shop.module.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * sysUserId
     */
    @Schema(description = "平台用户id")
    private Long sysUserId;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 员工编号
     */
    @Schema(description = "员工编号")
    private String code;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    private String phoneNum;

    @Schema(description = "是否已经有账号了")
    private Integer hasAccount;

    @Schema(description = "平台id")
    private Long shopId;

    @Schema(description = "角色id列表")
    private List<Long> roleIds;
}
