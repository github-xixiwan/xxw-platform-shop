package com.xxw.shop.api.search.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EsOrderItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品图片")
    private String pic;

    @Schema(description = "商品名称")
    private String spuName;

    @Schema(description = "商品数量")
    private Integer count;

    @Schema(description = "商品价格")
    private Long price;

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "skuName")
    private String skuName;

    @Schema(description = "订单项id")
    private Long orderItemId;

    @Schema(description = "商品id")
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
     * 单个orderItem的配送类型 3：无需快递
     */
    private Integer deliveryType;

    /**
     * 加入购物车时间
     */
    private LocalDateTime shopCartTime;

    /**
     * 商品总金额
     */
    private Long spuTotalAmount;
}
