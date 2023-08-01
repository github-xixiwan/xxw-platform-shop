package com.xxw.shop.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "auth_account")
public class AuthAccount implements Serializable {
    /**
     * 全平台用户唯一id
     */
    @Id
    private BigInteger uid;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 用户名
     */
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
    private Integer status;
    /**
     * 用户类型见SysTypeEnum 0.普通用户系统 1.商家端 2平台端
     */
    private Integer sysType;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 所属租户
     */
    private Long tenantId;
    /**
     * 是否是管理员
     */
    private Integer isAdmin;

}
