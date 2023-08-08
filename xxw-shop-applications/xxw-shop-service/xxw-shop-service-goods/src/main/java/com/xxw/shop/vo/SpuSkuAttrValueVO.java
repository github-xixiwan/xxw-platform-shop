package com.xxw.shop.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpuSkuAttrValueVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品sku销售属性关联信息id")
    private Integer spuSkuAttrId;

    @Schema(description = "SPU ID")
    private Long spuId;

    @Schema(description = "SKU ID")
    private Long skuId;

    @Schema(description = "销售属性ID")
    private Integer attrId;

    @Schema(description = "销售属性名称")
    private String attrName;

    @Schema(description = "销售属性值ID")
    private Integer attrValueId;

    @Schema(description = "销售属性值")
    private String attrValueName;

    @Schema(description = "状态 1:enable, 0:disable, -1:deleted")
    private Integer status;
}
