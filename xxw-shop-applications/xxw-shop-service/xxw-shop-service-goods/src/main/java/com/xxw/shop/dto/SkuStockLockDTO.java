package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class SkuStockLockDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "产品ID不能为空")
    @Schema(description = "产品ID", required = true)
    private Long spuId;

    @NotNull(message = "skuId不能为空")
    @Schema(description = "skuId", required = true)
    private Long skuId;

    @NotNull(message = "orderId不能为空")
    @Schema(description = "orderId", required = true)
    private Long orderId;

    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量不能为空")
    @Schema(description = "商品数量", required = true)
    private Integer count;
}
