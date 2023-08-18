package com.xxw.shop.module.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShopCartItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 加入购物车时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 购物车ID
     */
    private Long cartItemId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 产品ID
     */
    private Long spuId;

    /**
     * SkuID
     */
    private Long skuId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 购物车产品个数
     */
    private Integer count;

    /**
     * 是否已经勾选
     */
    private Integer isChecked;

    /**
     * 售价，加入购物车时的商品价格
     */
    private Long priceFee;

    /**
     * 当前商品价格
     */
    private Long skuPriceFee;

    /**
     * 当前总价格(商品价格 * 数量)
     */
    private Long totalPriceFee;

    /**
     * 当前总价格(商品价格 * 数量)(带小数)
     */
    private Long totalPrice;

    /**
     * 商品重量
     */
    private BigDecimal weight;

    /**
     * 商品体积
     */
    private BigDecimal volume;

    /**
     * 商品图片
     */
    private String imgUrl;

    /**
     * 总金额
     */
    private Long totalAmount;

    /**
     * sku规格信息
     */
    private String skuName;

    /**
     * spu名称
     */
    private String spuName;
}
