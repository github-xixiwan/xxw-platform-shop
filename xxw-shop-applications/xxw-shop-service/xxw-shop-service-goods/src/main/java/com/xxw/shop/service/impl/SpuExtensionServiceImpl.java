package com.xxw.shop.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.dto.SpuExtensionQueryDTO;
import com.xxw.shop.entity.SpuExtension;
import com.xxw.shop.entity.table.SpuExtensionTableDef;
import com.xxw.shop.mapper.SpuExtensionMapper;
import com.xxw.shop.service.SpuExtensionService;
import com.xxw.shop.vo.SpuExtensionVO;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class SpuExtensionServiceImpl extends ServiceImpl<SpuExtensionMapper, SpuExtension> implements SpuExtensionService {

    @Override
    public Page<SpuExtensionVO> page(SpuExtensionQueryDTO dto) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        return this.pageAs(new Page<>(dto.getPageNumber(), dto.getPageSize()), queryWrapper, SpuExtensionVO.class);
    }

    @Override
    public SpuExtensionVO getBySpuExtendId(Long spuExtendId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SpuExtensionTableDef.SPU_EXTENSION.SPU_EXTEND_ID.eq(spuExtendId));
        return this.getOneAs(queryWrapper, SpuExtensionVO.class);
    }

    @Override
    public void updateStock(Long spuId, Integer count) {
        mapper.updateStock(spuId, count);
    }

    @Override
    public void deleteBySpuId(Long spuId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SpuExtensionTableDef.SPU_EXTENSION.SPU_ID.eq(spuId));
        this.remove(queryWrapper);
    }

    @Override
    public SpuExtensionVO getBySpuId(Long spuId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SpuExtensionTableDef.SPU_EXTENSION.SPU_ID.eq(spuId));
        return this.getOneAs(queryWrapper, SpuExtensionVO.class);
    }
}
