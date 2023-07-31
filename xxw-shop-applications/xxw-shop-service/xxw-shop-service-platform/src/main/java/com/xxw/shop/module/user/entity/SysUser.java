package com.xxw.shop.module.user.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 *  实体类。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_user")
public class SysUser implements Serializable {
    /**
     * 平台用户id
     */
    @Id
    private BigInteger sysUserId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 员工编号
     */
    private String code;
    /**
     * 联系方式
     */
    private String phoneNum;
    /**
     * 是否已经设置账号
     */
    private Integer hasAccount;

}
