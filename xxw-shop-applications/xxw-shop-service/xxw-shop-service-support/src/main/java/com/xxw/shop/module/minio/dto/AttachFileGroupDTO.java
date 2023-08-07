package com.xxw.shop.module.minio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttachFileGroupDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema()
    private Long attachFileGroupId;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "分组名称")
    private String name;
}
