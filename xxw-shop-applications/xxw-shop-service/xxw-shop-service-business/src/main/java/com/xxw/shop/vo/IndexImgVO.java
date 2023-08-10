package com.xxw.shop.vo;

import com.xxw.shop.api.goods.vo.SpuVO;
import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class IndexImgVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long imgId;

    @Schema(description = "店铺ID")
    private Long shopId;

    @Schema(description = "图片")
    private String imgUrl;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "顺序")
    private Integer seq;

    @Schema(description = "关联商品id")
    private Long spuId;

    @Schema(description = "图片类型 0:小程序 1:pc")
    private Integer imgType;

    @Schema(description = "spu信息")
    private SpuVO spu;
}
