package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttrDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "attr id")
    private Long attrId;

    @Schema(description = "店铺id")
    private Long shopId;

    @NotNull(message = "属性名称不能为空")
    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "属性描述")
    private String desc;

    @Schema(description = "作为搜索参数 0:不需要，1:需要")
    private Integer searchType;

    @Schema(description = "属性类型 0:销售属性，1:基本属性")
    private Integer attrType;

    @Schema(description = "分类id列表")
    private List<Long> categoryIds;

    @Schema(description = "属性值列表")
    private List<AttrValueDTO> attrValues;
}
