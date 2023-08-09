package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.cache.GoodsCacheNames;
import com.xxw.shop.constant.GoodsBusinessError;
import com.xxw.shop.dto.BrandDTO;
import com.xxw.shop.dto.BrandQueryDTO;
import com.xxw.shop.entity.Brand;
import com.xxw.shop.mapper.BrandMapper;
import com.xxw.shop.module.cache.tool.IGlobalRedisCache;
import com.xxw.shop.module.common.cache.CacheNames;
import com.xxw.shop.module.common.constant.StatusEnum;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.service.BrandService;
import com.xxw.shop.service.CategoryBrandService;
import com.xxw.shop.service.SpuService;
import com.xxw.shop.api.goods.vo.BrandVO;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.xxw.shop.entity.table.BrandTableDef.BRAND;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Resource
    private IGlobalRedisCache globalRedisCache;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private SpuService spuService;

    @Resource
    private CategoryBrandService categoryBrandService;

    @Override
    public Page<BrandVO> page(BrandQueryDTO dto) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(BRAND.NAME.eq(dto.getName()));
        queryWrapper.and(BRAND.STATUS.eq(dto.getStatus()));
        queryWrapper.orderBy(BRAND.BRAND_ID.desc());
        return this.pageAs(new Page<>(dto.getPageNumber(), dto.getPageSize()), queryWrapper, BrandVO.class);
    }

    @Override
    public BrandVO getByBrandId(Long brandId) {
        return mapper.getByBrandId(brandId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBrand(BrandDTO dto, List<Long> categoryIds) {
        dto.setFirstLetter(dto.getFirstLetter().toUpperCase());
        dto.setStatus(StatusEnum.ENABLE.value());
        Brand brand = mapperFacade.map(dto, Brand.class);
        this.save(brand);
        categoryBrandService.saveByCategoryIds(dto.getBrandId(), categoryIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(BrandDTO dto, List<Long> categoryIds) {
        Brand brand = mapperFacade.map(dto, Brand.class);
        this.updateById(brand);
        categoryBrandService.updateByCategoryIds(dto.getBrandId(), categoryIds);
    }

    @Override
    public void deleteById(Long brandId) {
        if (spuService.getUseNum(brandId) > 0) {
            throw new BusinessException(GoodsBusinessError.GOODS_00002);
        }
        this.removeById(brandId);
        categoryBrandService.deleteByBrandId(brandId);
    }

    @Override
    public List<BrandVO> getBrandByCategoryId(Long categoryId) {
        return mapper.getBrandByCategoryId(categoryId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = GoodsCacheNames.BRAND_TOP)
    public void updateBrandStatus(BrandDTO dto) {
        BrandVO dbBrand = getByBrandId(dto.getBrandId());
        if (Objects.isNull(dbBrand) || Objects.equals(dbBrand.getStatus(), dto.getStatus())) {
            return;
        }
        Brand brand = mapperFacade.map(dto, Brand.class);
        this.updateById(brand);
    }

    @Override
    @Cacheable(cacheNames = GoodsCacheNames.BRAND_LIST_BY_CATEGORY, key = "#categoryId")
    public List<BrandVO> listByCategory(Long categoryId) {
        return mapper.listByCategory(categoryId);
    }

    @Override
    @Cacheable(cacheNames = GoodsCacheNames.BRAND_TOP)
    public List<BrandVO> topBrandList() {
        return mapper.topBrandList();
    }

    @Override
    @CacheEvict(cacheNames = GoodsCacheNames.BRAND_TOP)
    public void removeCache(List<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return;
        }
        List<String> keys = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            keys.add(GoodsCacheNames.BRAND_LIST_BY_CATEGORY + CacheNames.UNION + categoryId);
        }
        globalRedisCache.del(keys.toArray(new String[0]));
    }
}
