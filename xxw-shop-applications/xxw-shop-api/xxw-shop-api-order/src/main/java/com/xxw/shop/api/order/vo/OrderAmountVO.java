package com.xxw.shop.api.order.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderAmountVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付金额
     */
    private Long payAmount;
}
