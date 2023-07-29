package com.xxw.shop.module.user.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author liaoxiting
 * @since 2023-07-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "users")
public class Users implements Serializable {
    /**
     * 主键id 用户id
     */
    @Id
    private String id;
    /**
     * 用户名 用户名
     */
    private String username;
    /**
     * 密码 密码
     */
    private String password;
    /**
     * 昵称 昵称
     */
    private String nickname;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 头像
     */
    private String face;
    /**
     * 手机号 手机号
     */
    private String mobile;
    /**
     * 邮箱地址 邮箱地址
     */
    private String email;
    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;
    /**
     * 生日 生日
     */
    private Date birthday;
    /**
     * 创建时间 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 更新时间 更新时间
     */
    private LocalDateTime updatedTime;

}
