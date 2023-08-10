package com.xxw.shop.api.search.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttrSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "规格id")
    private Long attrId;

    @Schema(description = "规格名")
    private String attrName;

    @Schema(description = "规格值列表")
    private List<AttrValueSearchVO> attrValues;
}
