package com.xxw.shop.module.common.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EsOrderItemBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 商品价格
     */
    private Long price;

    /**
     * skuId
     */
    private Long skuId;

    /**
     * skuName
     */
    private String skuName;

    /**
     * 订单项id
     */
    private Long orderItemId;

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 单个orderItem的配送类型  ：无需快递
     */
    private Integer deliveryType;

    /**
     * 商品总金额
     */
    private Long spuTotalAmount;
}
