package com.xxw.shop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "购物车合计")
public class ShopCartAmountVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "总额")
    private Long totalMoney;

    @Schema(description = "总计")
    private Long finalMoney;

    @Schema(description = "减额")
    private Long subtractMoney;

    @Schema(description = "商品数量")
    private Integer count;
}
