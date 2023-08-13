package com.xxw.shop.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "shop_user")
public class ShopUser implements Serializable {

    /**
     * 商家用户id
     */
    @Id
    private Long shopUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 关联店铺id
     */
    private Long shopId;

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
     * 是否已经设置账号 0:未设置 1:已设置
     */
    private Integer hasAccount;

}
