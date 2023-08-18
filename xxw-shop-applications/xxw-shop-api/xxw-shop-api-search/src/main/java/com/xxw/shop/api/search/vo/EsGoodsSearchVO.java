package com.xxw.shop.api.search.vo;

import com.xxw.shop.module.common.bo.EsGoodsBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EsGoodsSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺信息")
    private ShopInfoSearchVO shopInfo;

    @Schema(description = "规格属性列表")
    private List<AttrSearchVO> attrs;

    @Schema(description = "品牌列表信息")
    private List<BrandSearchVO> brands;

    @Schema(description = "spu列表信息")
    private List<EsGoodsBO> spus;

    @Schema(description = "分类列表信息")
    private List<CategorySearchVO> categorys;
}
