package com.xxw.shop.module.user.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
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
 * @since 2023-07-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_role")
public class UserRole implements Serializable {
    /**
     * 关联id
     */
    @Id(keyType = KeyType.Auto)
    private BigInteger id;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 用户ID
     */
    private BigInteger userId;
    /**
     * 角色ID
     */
    private BigInteger roleId;

}
