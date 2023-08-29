package com.xxw.shop.api.search.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xxw.shop.api.support.serializer.ImgJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class BrandSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "品牌名称")
    private String brandName;

    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "品牌图片")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String brandImg;
}
