package com.xxw.shop.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayInfoResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商城支付单号
     */
    private Long payId;

    /**
     * 第三方订单流水号
     */
    private String bizPayNo;

    /**
     * 是否支付成功
     */
    private Integer isPaySuccess;

    /**
     * 支付成功的标记
     */
    private String successString;

    /**
     * 支付金额
     */
    private Long payAmount;

    /**
     * 回调内容
     */
    private String callbackContent;
}
