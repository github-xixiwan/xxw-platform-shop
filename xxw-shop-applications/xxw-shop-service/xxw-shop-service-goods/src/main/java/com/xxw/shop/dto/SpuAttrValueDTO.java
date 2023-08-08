package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SpuAttrValueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品属性值关联信息id")
    private Long spuAttrValueId;

    @Schema(description = "商品id")
    private Long spuId;

    @Schema(description = "规格属性id")
    private Long attrId;

    @Schema(description = "规格属性名称")
    private String attrName;

    @Schema(description = "规格属性值id")
    private Long attrValueId;

    @Schema(description = "规格属性值名称")
    private String attrValueName;
}
