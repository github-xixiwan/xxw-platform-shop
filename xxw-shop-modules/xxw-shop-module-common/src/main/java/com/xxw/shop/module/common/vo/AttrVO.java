package com.xxw.shop.module.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规格id
     */
    private Long attrId;

    /**
     * 规格名
     */
    private String attrName;

    /**
     * 规格值id
     */
    private Long attrValueId;

    /**
     * 规格值名称
     */
    private String attrValueName;
}
