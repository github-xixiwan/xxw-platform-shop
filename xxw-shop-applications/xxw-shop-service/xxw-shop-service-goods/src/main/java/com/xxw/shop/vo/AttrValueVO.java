package com.xxw.shop.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AttrValueVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属性id")
    private Long attrValueId;

    @Schema(description = "属性ID")
    private Long attrId;

    @Schema(description = "属性值")
    private String value;
}
