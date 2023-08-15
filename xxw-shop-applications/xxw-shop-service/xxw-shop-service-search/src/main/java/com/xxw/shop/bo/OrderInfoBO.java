package com.xxw.shop.bo;

import com.xxw.shop.starter.canal.annotation.CanalModel;
import com.xxw.shop.starter.canal.common.FieldNamingPolicy;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品信息
 */
@Data
@CanalModel(database = "xxw-shop", table = "order_info", fieldNamingPolicy = FieldNamingPolicy.LOWER_UNDERSCORE)
public class OrderInfoBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 总值
     */
    private Long total;

    /**
     * 订单状态 1:待付款 2:待发货3:待收货(已发货) 5:成功 6:失败
     */
    private Integer status;

    /**
     * 配送类型 3：无需快递
     */
    private Integer deliveryType;

    /**
     * 订单关闭原因 1-超时未支付 4-买家取消 15-已通过货到付款交易
     */
    private Integer closeType;

    /**
     * 订单商品总数
     */
    private Integer allCount;

    /**
     * 付款时间
     */
    private Date payTime;

    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 完成时间
     */
    private Date finallyTime;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 是否已支付，1.已支付0.未支付
     */
    private Integer isPayed;

    /**
     * 用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除
     */
    private Integer deleteStatus;
}
