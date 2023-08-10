package com.xxw.shop.api.order.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderSimpleAmountInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orderId;

    private Long shopId;

    /**
     * 实际总值
     */
    private Long actualTotal;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单关闭原因
     */
    private Integer closeType;
}
