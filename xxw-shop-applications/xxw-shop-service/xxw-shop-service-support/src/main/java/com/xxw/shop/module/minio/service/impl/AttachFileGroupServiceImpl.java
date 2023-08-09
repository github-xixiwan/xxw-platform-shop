package com.xxw.shop.module.minio.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.module.minio.entity.AttachFileGroup;
import com.xxw.shop.module.minio.mapper.AttachFileGroupMapper;
import com.xxw.shop.module.minio.mapper.AttachFileMapper;
import com.xxw.shop.module.minio.service.AttachFileGroupService;
import com.xxw.shop.module.minio.vo.AttachFileGroupVO;
import com.xxw.shop.module.security.AuthUserContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.xxw.shop.module.minio.entity.table.AttachFileGroupTableDef.ATTACH_FILE_GROUP;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-07
 */
@Service
public class AttachFileGroupServiceImpl extends ServiceImpl<AttachFileGroupMapper, AttachFileGroup> implements AttachFileGroupService {

    @Resource
    private AttachFileMapper attachFileMapper;

    @Override
    public List<AttachFileGroupVO> listByShopId() {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(ATTACH_FILE_GROUP.SHOP_ID.eq(AuthUserContext.get().getTenantId()));
        queryWrapper.orderBy(ATTACH_FILE_GROUP.ATTACH_FILE_GROUP_ID.desc());
        return this.listAs(queryWrapper, AttachFileGroupVO.class);
    }

    @Override
    public AttachFileGroupVO getByAttachFileGroupId(Long attachFileGroupId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(ATTACH_FILE_GROUP.ATTACH_FILE_GROUP_ID.eq(attachFileGroupId));
        return this.getOneAs(queryWrapper, AttachFileGroupVO.class);
    }

    @Override
    public void saveAttachFileGroup(AttachFileGroup attachFileGroup) {
        attachFileGroup.setShopId(AuthUserContext.get().getTenantId());
        this.save(attachFileGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long attachFileGroupId) {
        this.removeById(attachFileGroupId);
        attachFileMapper.updateBatchByAttachFileGroupId(attachFileGroupId);
    }
}
