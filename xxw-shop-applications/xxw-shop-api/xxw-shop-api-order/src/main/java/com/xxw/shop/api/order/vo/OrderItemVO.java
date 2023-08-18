package com.xxw.shop.api.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderItemVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单项ID")
    private Long orderItemId;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "订单id")
    private Long orderId;

    @Schema(description = "产品ID")
    private Long spuId;

    @Schema(description = "产品SkuID")
    private Long skuId;

    @Schema(description = "用户Id")
    private Long userId;

    @Schema(description = "购物车产品个数")
    private Integer count;

    @Schema(description = "产品名称")
    private String spuName;

    @Schema(description = "sku名称")
    private String skuName;

    @Schema(description = "产品主图片路径")
    private String pic;

    @Schema(description = "单个orderItem的配送类型 3：无需快递")
    private Integer deliveryType;

    @Schema(description = "加入购物车时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime shopCartTime;

    @Schema(description = "产品价格")
    private Long price;

    @Schema(description = "商品总金额")
    private Long spuTotalAmount;

    @Schema(description = "发货改变的数量")
    private Integer changeNum;
}
