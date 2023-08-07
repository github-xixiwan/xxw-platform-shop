package com.xxw.shop.module.minio.dto;

import com.xxw.shop.module.common.page.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class AttachFileQueryDTO extends PageDTO<AttachFileQueryDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName;

    private Long fileGroupId;
}