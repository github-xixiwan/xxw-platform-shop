package com.xxw.shop.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  实体类。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "index_img")
public class IndexImg implements Serializable {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long imgId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 图片
     */
    private String imgUrl;

    /**
     * 状态 1:enable, 0:disable
     */
    private Integer status;

    /**
     * 顺序
     */
    private Integer seq;

    /**
     * 关联商品id
     */
    private Long spuId;

    /**
     * 图片类型 0:小程序
     */
    private Integer imgType;

}
