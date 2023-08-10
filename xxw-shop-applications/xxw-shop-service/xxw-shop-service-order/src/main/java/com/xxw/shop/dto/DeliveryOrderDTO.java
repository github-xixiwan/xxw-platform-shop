package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeliveryOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "deliveryOrderId")
    private Long deliveryOrderId;

    @NotNull(message = "订单号不能为空")
    @Schema(description = "订单号", required = true)
    private Long orderId;

    @NotNull(message = "发货方式不能为空")
    @Schema(description = "发货方式", required = true)
    private Integer deliveryType;

    private List<DeliveryOrderItemDTO> selectOrderItems;
}
