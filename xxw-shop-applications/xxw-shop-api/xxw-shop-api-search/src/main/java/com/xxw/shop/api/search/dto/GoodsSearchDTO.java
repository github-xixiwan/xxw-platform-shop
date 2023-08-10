package com.xxw.shop.api.search.dto;


import com.xxw.shop.module.common.page.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsSearchDTO extends PageDTO<GoodsSearchDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "页面传递过来的全文匹配关键字")
    private String keyword;

    @Schema(description = "品牌id,可以多选")
    private String brandIds;

    @Schema(description = "商家一级分类id")
    private Long shopPrimaryCategoryId;

    @Schema(description = "商家二级分类id")
    private Long shopSecondaryCategoryId;

    @Schema(description = "平台一级分类id")
    private Long primaryCategoryId;

    @Schema(description = "平台三级分类id")
    private Long categoryId;

    @Schema(description = "排序：1新品,2销量倒序,3销量正序,4商品价格倒序,5商品价格正序,6评论(暂无评论)")
    private Integer sort;

    @Schema(description = "自营店 1：自营店 2：非自营店")
    private Integer selfShop;

    @Schema(description = "是否显示有货")
    private Integer hasStock;

    @Schema(description = "价格区间查询-最低价")
    private Long minPrice;

    @Schema(description = "价格区间查询-最高价")
    private Long maxPrice;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "属性值ids(属性之间用^拼接；属性于属性值id用_拼接；多个属性值id间用,拼接)")
    private String attrIds;

    @Schema(description = "商品状态")
    private Integer spuStatus;

    @Schema(description = "属性值ids(多个id间使用 , 分隔)")
    private String attrValueIds;

    @Schema(description = "spuId列表")
    private List<Long> spuIds;

    @Schema(description = "销量区间查询-最低销量")
    private Long minSaleNum;

    @Schema(description = "销量区间查询-最高销量")
    private Long maxSaleNum;

    @Schema(description = "商品编码列表（逗号分隔）")
    private String partyCodes;

    @Schema(description = "商品条形码列表（逗号分隔）")
    private String modelIds;

    @Schema(description = "0.全部  1.销售中  2.已售罄  3.已下架")
    private Integer dataType;

    /**
     * 对应SearchTypeEnum
     * 搜索类型 1：用户端搜索  2：店铺spu列表 3.平台spu管理列表
     */
    private Integer searchType;

    /**
     * 搜索属性信息
     */
    private Map<String, List<String>> attrMap;
}
