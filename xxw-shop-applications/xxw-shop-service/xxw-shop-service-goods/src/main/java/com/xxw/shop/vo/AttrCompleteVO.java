package com.xxw.shop.vo;

import com.mybatisflex.annotation.Column;
import com.xxw.shop.api.goods.vo.CategoryVO;
import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AttrCompleteVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "attr id")
    private Long attrId;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "属性描述")
    private String desc;

    @Schema(description = "作为搜索参数 0:不需要，1:需要")
    private Integer searchType;

    @Schema(description = "属性类型 0:销售属性，1:基本属性")
    private Integer attrType;

    @Schema(description = "属性值列表")
    private List<AttrValueVO> attrValues;

    @Column(ignore = true)
    @Schema(description = "分类列表")
    private List<CategoryVO> categories;
}
