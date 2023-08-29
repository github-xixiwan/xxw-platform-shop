package com.xxw.shop.api.search.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EsOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单项
     */
    private List<EsOrderItemVO> orderItems;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 总价
     */
    private Long actualTotal;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 配送类型 :无需快递
     */
    private Integer deliveryType;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 商品总数
     */
    private Integer allCount;

    /**
     * 收货人姓名
     */
    private String consignee;

    /**
     * 收货人手机号
     */
    private String mobile;
    /**
     * 用户订单地址Id
     */
    private Long orderAddrId;

    /**
     * 总值
     */
    private Long total;

    /**
     * 支付方式 请参考枚举PayType
     */
    private Integer payType;

    /**
     * 订单关闭原因 1-超时未支付  4-买家取消 15-已通过货到付款交易
     */
    private Integer closeType;

    /**
     * 是否已支付，1.已支付0.未支付
     */
    private Integer isPayed;

    /**
     * 用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除
     */
    private Integer deleteStatus;
}
