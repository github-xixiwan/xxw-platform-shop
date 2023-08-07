package com.xxw.shop.module.minio.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AttachFileGroupVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema()
    private Long attachFileGroupId;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "分组名称")
    private String name;
}
