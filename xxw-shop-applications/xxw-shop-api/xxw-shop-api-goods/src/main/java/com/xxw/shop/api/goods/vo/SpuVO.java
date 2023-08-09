package com.xxw.shop.api.goods.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpuVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "spu id")
    private Long spuId;

    @Schema(description = "品牌ID")
    private Long brandId;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "店铺分类ID")
    private Long shopCategoryId;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "spu名称")
    private String name;

    @Schema(description = "卖点")
    private String sellingPoint;

    @Schema(description = "商品介绍主图")
    private String mainImgUrl;

    @Schema(description = "商品介绍主图 多个图片逗号分隔")
    private String imgUrls;

    @Schema(description = "售价，整数方式保存")
    private Long priceFee;

    @Schema(description = "市场价，整数方式保存")
    private Long marketPriceFee;

    @Schema(description = "状态 1:enable, 0:disable, -1:deleted")
    private Integer status;

    @Schema(description = "sku是否含有图片 0无 1有")
    private Integer hasSkuImg;

    @Schema(description = "商品详情")
    private String detail;

    @Schema(description = "总库存")
    private Integer totalStock;

    @Schema(description = "规格属性")
    private List<SpuAttrValueVO> spuAttrValues;

    @Schema(description = "sku列表")
    private List<SkuVO> skus;

    @Schema(description = "序号")
    private Integer seq;

    @Schema(description = "品牌信息")
    private BrandVO brand;

    @Schema(description = "商品销量")
    private Integer saleNum;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "分类信息")
    private CategoryVO category;

    @Schema(description = "店铺分类信息")
    private CategoryVO shopCategory;

    @Schema(description = "分组商品关联id")
    private Long referenceId;
}
