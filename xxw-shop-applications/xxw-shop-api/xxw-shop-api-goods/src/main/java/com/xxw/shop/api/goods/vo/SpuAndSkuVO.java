package com.xxw.shop.api.goods.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SpuAndSkuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "spu信息")
    private SpuVO spu;

    @Schema(description = "sku信息")
    private SkuVO sku;
}
