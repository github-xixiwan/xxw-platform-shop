package com.xxw.shop.module.minio.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.module.minio.entity.AttachFileGroup;
import com.xxw.shop.module.minio.vo.AttachFileGroupVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-07
 */
public interface AttachFileGroupService extends IService<AttachFileGroup> {

    List<AttachFileGroupVO> listByShopId();

    AttachFileGroupVO getByAttachFileGroupId(Long attachFileGroupId);

    void saveAttachFileGroup(AttachFileGroup attachFileGroup);

    void deleteById(Long attachFileGroupId);

}
