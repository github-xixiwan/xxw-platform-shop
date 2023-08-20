package com.xxw.shop.module.common.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoInTokenBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户在自己系统的用户id
     */
    private Long userId;

    /**
     * 全局唯一的id,
     */
    private Long uid;

    /**
     * 租户id (商家id)
     */
    private Long tenantId;

    /**
     * 系统类型
     */
    private Integer sysType;

    /**
     * 是否是管理员
     */
    private Integer isAdmin;

    private String bizUserId;

    private String bizUid;
}
