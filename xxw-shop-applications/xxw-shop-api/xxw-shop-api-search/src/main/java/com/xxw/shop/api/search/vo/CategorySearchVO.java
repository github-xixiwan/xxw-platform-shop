package com.xxw.shop.api.search.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategorySearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分类id")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String name;
}
