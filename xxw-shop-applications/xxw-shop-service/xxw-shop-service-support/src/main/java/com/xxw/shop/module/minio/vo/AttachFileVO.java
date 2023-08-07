package com.xxw.shop.module.minio.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AttachFileVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema()
    private Long fileId;

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件大小")
    private Integer fileSize;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "文件 1:图片 2:视频 3:文件")
    private Integer type;

    @Schema(description = "分组id")
    private Long attachFileGroupId;
}
