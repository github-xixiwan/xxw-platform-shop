package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeliveryOrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long orderItemId;

    @Schema(description = "商品图片")
    private String pic;

    @Schema(description = "商品名称")
    private String spuName;

    @Schema(description = "发货改变的数量")
    private Integer changeNum;
}
