package com.xxw.shop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 提交订单的支付信息
 */
@Data
public class SubmitOrderPayInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品名称")
    private List<String> spuNameList;

    @Schema(description = "收货人姓名")
    private String consignee;

    @Schema(description = "收货地址")
    private String userAddr;

    @Schema(description = "收货人手机号")
    private String mobile;

    @Schema(description = "订单过期时间")
    private LocalDateTime endTime;

    @Schema(description = "总共需要支付金额")
    private Long totalFee;
}
