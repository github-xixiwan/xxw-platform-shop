package com.xxw.shop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SubmitOrderPayAmountInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "总共需要支付金额")
    private Long totalFee;

    @Schema(description = "订单地址id")
    private Long orderAddrId;
}
