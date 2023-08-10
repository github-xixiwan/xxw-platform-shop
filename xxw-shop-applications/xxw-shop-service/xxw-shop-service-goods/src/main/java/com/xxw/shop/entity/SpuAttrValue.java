package com.xxw.shop.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 实体类。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "spu_attr_value")
public class SpuAttrValue implements Serializable {

    /**
     * 商品属性值关联信息id
     */
    @Id(keyType = KeyType.Auto)
    private Long spuAttrValueId;

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 规格属性id
     */
    private Long attrId;

    /**
     * 规格属性名称
     */
    private String attrName;

    /**
     * 规格属性值id
     */
    private Long attrValueId;

    /**
     * 规格属性值名称
     */
    private String attrValueName;

    /**
     * 规格属性描述
     */
    private String attrDesc;

}
