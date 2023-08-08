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
 * @since 2023-08-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "attr")
public class Attr implements Serializable {

    /**
     * attr id
     */
    @Id(keyType = KeyType.Auto)
    private Long attrId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 店铺Id
     */
    private Long shopId;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性描述
     */
    private String desc;

    /**
     * 0:不需要，1:需要
     */
    private Integer searchType;

    /**
     * 0:销售属性，1:基本属性
     */
    private Integer attrType;

}
