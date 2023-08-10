package com.xxw.shop.api.search.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttrValueSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "规格值id")
    private Long attrValueId;

    @Schema(description = "规格值名称")
    private String attrValueName;
}
