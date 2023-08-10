package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PayInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "订单号不能为空")
    @Schema(description = "订单号", required = true)
    private List<Long> orderIds;

    @Schema(description = "支付完成回跳地址", required = true)
    private String returnUrl;
}
