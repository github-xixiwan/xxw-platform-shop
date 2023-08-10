package com.xxw.shop.api.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EsOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单项", required = true)
    private List<EsOrderItemVO> orderItems;

    @Schema(description = "用户id", required = true)
    private Long userId;

    @Schema(description = "订单号", required = true)
    private Long orderId;

    @Schema(description = "总价", required = true)
    private Long actualTotal;

    @Schema(description = "订单状态", required = true)
    private Integer status;

    @Schema(description = "配送类型 :无需快递", required = true)
    private Integer deliveryType;

    @Schema(description = "店铺名称", required = true)
    private String shopName;

    @Schema(description = "店铺id", required = true)
    private Long shopId;

    @Schema(description = "订单创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "商品总数", required = true)
    private Integer allCount;

    @Schema(description = "收货人姓名")
    private String consignee;

    @Schema(description = "收货人手机号")
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
     * 发货时间
     */
    private LocalDateTime updateTime;

    /**
     * 付款时间
     */
    private LocalDateTime payTime;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 完成时间
     */
    private LocalDateTime finallyTime;

    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 是否已支付，1.已支付0.未支付
     */
    private Integer isPayed;

    /**
     * 用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除
     */
    private Integer deleteStatus;
}
