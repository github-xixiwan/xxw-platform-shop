package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttrValueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属性id")
    private Long attrValueId;

    @Schema(description = "属性ID")
    private Long attrId;

    @Schema(description = "属性值")
    private String value;

}
