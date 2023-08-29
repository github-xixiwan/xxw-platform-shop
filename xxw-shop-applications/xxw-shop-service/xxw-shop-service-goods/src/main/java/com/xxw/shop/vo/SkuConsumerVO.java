package com.xxw.shop.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xxw.shop.api.support.serializer.ImgJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SkuConsumerVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属性id")
    private Long skuId;

    @Schema(description = "SPU id")
    private Long spuId;

    @Schema(description = "sku名称")
    private String skuName;

    @Schema(description = "banner图片")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String imgUrl;

    @Schema(description = "售价，整数方式保存")
    private Long priceFee;

    @Schema(description = "市场价，整数方式保存")
    private Long marketPriceFee;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "属性")
    private String properties;
}
