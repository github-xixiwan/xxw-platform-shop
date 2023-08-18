package com.xxw.shop.api.search.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime shopCartTime;

    /**
     * 商品总金额
     */
    private Long spuTotalAmount;
}
