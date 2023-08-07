package com.xxw.shop.module.minio.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.module.minio.entity.AttachFile;
import org.apache.ibatis.annotations.Param;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-07
 */
public interface AttachFileMapper extends BaseMapper<AttachFile> {

    /**
     * 批量更新文件的分组
     *
     * @param attachFileGroupId
     */
    void updateBatchByAttachFileGroupId(@Param("attachFileGroupId") Long attachFileGroupId);
}
