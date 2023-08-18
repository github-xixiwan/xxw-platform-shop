package com.xxw.shop.api.search.vo;

import lombok.Data;

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
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
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
