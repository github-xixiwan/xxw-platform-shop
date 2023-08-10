package com.xxw.shop.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderInfoVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "总值")
    private Long total;

    @Schema(description = "订单状态 1:待付款 2:待发货 3:待收货(已发货) 5:成功 6:失败")
    private Integer status;

    @Schema(description = "配送类型 3：无需快递")
    private Integer deliveryType;

    @Schema(description = "订单关闭原因 1-超时未支付 4-买家取消 15-已通过货到付款交易")
    private Integer closeType;

    @Schema(description = "订单商品总数")
    private Integer allCount;

    @Schema(description = "付款时间")
    private LocalDateTime payTime;

    @Schema(description = "发货时间")
    private LocalDateTime deliveryTime;

    @Schema(description = "完成时间")
    private LocalDateTime finallyTime;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "是否已支付，1.已支付0.未支付")
    private Integer isPayed;

    @Schema(description = "用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除")
    private Integer deleteStatus;

    @Schema(description = "订单项")
    private List<OrderItemVO> orderItems;

    @Schema(description = "订单地址")
    private OrderAddrVO orderAddr;
}
