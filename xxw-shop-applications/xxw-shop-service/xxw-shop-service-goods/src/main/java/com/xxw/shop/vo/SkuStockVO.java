package com.xxw.shop.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class SkuStockVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "库存id")
    private Long stockId;

    @Schema(description = "SKU ID")
    private Long skuId;

    @Schema(description = "库存")
    private Integer stock;
}
