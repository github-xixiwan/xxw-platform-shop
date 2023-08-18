package com.xxw.shop.api.search.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EsSpuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * spu id
     */
    private Long spuId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 店铺分类ID
     */
    private Long shopCategoryId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 卖点
     */
    private String sellingPoint;

    /**
     * 商品介绍主图
     */
    private String mainImgUrl;

    /**
     * 商品图片 多个图片逗号分隔
     */
    private String imgUrls;

    /**
     * 商品视频
     */
    private String video;

    /**
     * 售价，整数方式保存
     */
    private Long priceFee;

    /**
     * 市场价，整数方式保存
     */
    private Long marketPriceFee;

    /**
     * 状态 -1:删除, 0:下架, 1:上架
     */
    private Integer status;

    /**
     * sku是否含有图片 0无 1有
     */
    private Integer hasSkuImg;

    /**
     * 序号
     */
    private Integer seq;
}
