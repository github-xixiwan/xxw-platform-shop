package com.xxw.shop.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.entity.Spu;
import com.xxw.shop.mapper.SpuMapper;
import com.xxw.shop.service.SpuService;
import org.springframework.stereotype.Service;

import static com.xxw.shop.entity.table.SpuTableDef.SPU;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

    @Override
    public long getUseNum(Long brandId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SPU.BRAND_ID.eq(brandId));
        return this.count(queryWrapper);
    }
}
