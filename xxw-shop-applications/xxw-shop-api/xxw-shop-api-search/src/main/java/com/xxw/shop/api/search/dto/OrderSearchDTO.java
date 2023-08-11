package com.xxw.shop.api.search.dto;

import com.xxw.shop.module.common.page.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderSearchDTO extends PageDTO<OrderSearchDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 店铺id
     */
    private Long shopId;

    @Schema(description = "订单状态")
    private Integer status;

    @Schema(description = "是否已经支付，1：已经支付过，0：没有支付过")
    private Integer isPayed;

    /**
     * 订购流水号
     */
    @Schema(description = "订单号")
    private Long orderId;

    @Schema(description = "下单的时间范围:开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "下单的时间范围:结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "商品名称")
    private String spuName;

    @Schema(description = "收货人姓名")
    private String consignee;

    @Schema(description = "收货人手机号")
    private String mobile;

    @Schema(description = "物流类型 3：无需快递")
    private Integer deliveryType;
}
