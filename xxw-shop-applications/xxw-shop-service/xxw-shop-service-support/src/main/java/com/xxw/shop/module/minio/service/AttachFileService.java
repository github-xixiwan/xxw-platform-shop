package com.xxw.shop.module.minio.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.module.minio.dto.AttachFileQueryDTO;
import com.xxw.shop.module.minio.entity.AttachFile;
import com.xxw.shop.module.minio.vo.AttachFileVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-07
 */
public interface AttachFileService extends IService<AttachFile> {

    Page<AttachFileVO> page(AttachFileQueryDTO dto);

    void saveAttachFile(List<AttachFile> attachFiles);

    void deleteById(Long fileId);

    void updateFileName(AttachFile attachFile);

}
