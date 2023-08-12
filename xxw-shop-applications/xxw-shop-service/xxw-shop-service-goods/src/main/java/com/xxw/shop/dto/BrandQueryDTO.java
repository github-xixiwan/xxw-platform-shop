package com.xxw.shop.dto;

import com.xxw.shop.module.common.page.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BrandQueryDTO extends PageDTO<BrandQueryDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "brand_id" )
    private Long brandId;

    @Schema(description = "品牌名称" )
    private String name;

    @Schema(description = "品牌描述" )
    private String desc;

    @Schema(description = "品牌logo图片" )
    private String imgUrl;

    @Schema(description = "检索首字母" )
    private String firstLetter;

    @Schema(description = "排序" )
    private Integer seq;

    @Schema(description = "状态 1:enable, 0:disable, -1:deleted" )
    private Integer status;

    @Schema(description = "分类" )
    private List<Long> categoryIds;
}