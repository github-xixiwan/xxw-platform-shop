package com.xxw.shop.vo;

import com.xxw.shop.api.order.vo.OrderAddrVO;
import com.xxw.shop.api.order.vo.OrderItemVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单下的每个店铺
 */
@Data
public class OrderShopVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "商品总值")
    private Long total;

    @Schema(description = "商品总数")
    private Integer totalNum;


    @Schema(description = "地址Dto")
    private OrderAddrVO orderAddr;

    @Schema(description = "产品信息")
    private List<OrderItemVO> orderItems;

    @Schema(description = "订单创建时间")
    private LocalDateTime createTime;

    @Schema(description = "订单付款时间")
    private LocalDateTime payTime;

    @Schema(description = "订单发货时间")
    private LocalDateTime deliveryTime;

    @Schema(description = "订单完成时间")
    private LocalDateTime finallyTime;

    @Schema(description = "订单取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "订单更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "配送类型 3：无需快递")
    private Integer deliveryType;

    @Schema(description = "订单状态")
    private Integer status;
}
