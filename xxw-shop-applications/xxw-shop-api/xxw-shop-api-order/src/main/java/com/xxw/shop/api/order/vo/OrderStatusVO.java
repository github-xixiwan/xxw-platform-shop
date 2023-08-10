package com.xxw.shop.api.order.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderStatusVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orderId;

    private Integer status;
}
