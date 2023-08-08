package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class CheckShopCartItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "购物车ID", required = true)
    private Long shopCartItemId;

    @NotNull
    @Schema(description = "商品是否勾选 1:勾选 0:未勾选")
    private Integer isChecked;
}
