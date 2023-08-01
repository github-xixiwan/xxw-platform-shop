package com.xxw.shop.module.config.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema()
    private Long id;

    @Schema(description = "key")
    private String paramKey;

    @Schema(description = "value")
    private String paramValue;

    @Schema(description = "备注")
    private String remark;
}
