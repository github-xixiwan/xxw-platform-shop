package com.xxw.shop.dto;

import com.xxw.shop.module.common.page.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class IndexImgQueryDTO extends PageDTO<IndexImgQueryDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long imgId;

    @Schema(description = "店铺ID")
    private Long shopId;

    @NotNull(message = "图片不能为空")
    @Schema(description = "图片")
    private String imgUrl;

    @Schema(description = "状态")
    private Integer status;

    @NotNull(message = "序号不能为空")
    @Schema(description = "顺序")
    private Integer seq;

    @Schema(description = "关联商品id")
    private Long spuId;

    @NotNull(message = "图片类型不能为空")
    @Schema(description = "图片类型 0:小程序 1:pc")
    private Integer imgType;
}