package com.xxw.shop.module.minio.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.module.minio.dto.AttachFileQueryDTO;
import com.xxw.shop.module.minio.entity.AttachFile;
import com.xxw.shop.module.minio.entity.table.AttachFileTableDef;
import com.xxw.shop.module.minio.mapper.AttachFileMapper;
import com.xxw.shop.module.minio.service.AttachFileService;
import com.xxw.shop.module.minio.vo.AttachFileVO;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.starter.minio.MinioComponent;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-07
 */
@Service
public class AttachFileServiceImpl extends ServiceImpl<AttachFileMapper, AttachFile> implements AttachFileService {

    @Resource
    private MinioComponent minioComponent;

    @Override
    public Page<AttachFileVO> page(AttachFileQueryDTO dto) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(AttachFileTableDef.ATTACH_FILE.SHOP_ID.eq(AuthUserContext.get().getTenantId()));
        queryWrapper.and(AttachFileTableDef.ATTACH_FILE.ATTACH_FILE_GROUP_ID.eq(dto.getFileGroupId()));
        queryWrapper.and(AttachFileTableDef.ATTACH_FILE.FILE_NAME.like(dto.getFileName()));
        queryWrapper.orderBy(AttachFileTableDef.ATTACH_FILE.UPDATE_TIME.desc(),
                AttachFileTableDef.ATTACH_FILE.FILE_ID.desc());
        return this.pageAs(new Page<>(dto.getPageNumber(), dto.getPageSize()), queryWrapper, AttachFileVO.class);
    }

    @Override
    public void saveAttachFile(List<AttachFile> attachFiles) {
        Long tenantId = AuthUserContext.get().getTenantId();
        attachFiles.forEach(l -> l.setShopId(tenantId));
        this.saveBatch(attachFiles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long fileId) {
        AttachFile attachFile = this.getById(fileId);
        deleteFile(attachFile.getFilePath());
        this.removeById(fileId);
    }

    @Override
    public void updateFileName(AttachFile attachFile) {
        if (Objects.isNull(attachFile.getFileName()) && Objects.isNull(attachFile.getAttachFileGroupId())) {
            return;
        }
        this.updateById(attachFile);
    }

    /**
     * 根据文件路径，删除文件
     *
     * @param filePath 文件路径
     */
    public void deleteFile(String filePath) {
        // 获取文件的实际路径--数据库中保存的文件路径为： / + 实际的文件路径
        if (StrUtil.isNotBlank(filePath)) {
            filePath = filePath.substring(1);
        }
        minioComponent.remove(filePath);
    }
}
