package com.xxw.shop.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.user.vo.AreaVO;
import com.xxw.shop.cache.UserCacheNames;
import com.xxw.shop.constant.UserBusinessError;
import com.xxw.shop.entity.Area;
import com.xxw.shop.mapper.AreaMapper;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.service.AreaService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.xxw.shop.entity.table.AreaTableDef.AREA;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-11
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    public List<AreaVO> lists() {
        QueryWrapper queryWrapper = QueryWrapper.create();
        return this.listAs(queryWrapper, AreaVO.class);
    }

    @Override
    @Cacheable(cacheNames = UserCacheNames.AREA_INFO_KEY, key = "'areaList'", sync = true)
    public List<AreaVO> getAreaListInfo() {
        List<AreaVO> areaList = mapper.getAreaListInfo();
        for (AreaVO province : areaList) {
            List<Long> cityAll = new ArrayList<>();
            for (AreaVO city : province.getAreas()) {
                cityAll.add(city.getAreaId());
                List<Long> areaAll = new ArrayList<>();
                for (AreaVO area : city.getAreas()) {
                    areaAll.add(area.getAreaId());
                }
                city.setAreaIds(areaAll);
            }
            province.setAreaIds(cityAll);
        }
        return areaList;
    }

    @Override
    public List<AreaVO> listAreaOfEnable() {
        return mapper.listAreaOfEnable();
    }

    @Override
    public AreaVO getByAreaId(Long areaId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(AREA.AREA_ID.eq(areaId));
        return this.getOneAs(queryWrapper, AreaVO.class);
    }

    @Override
    public void deleteById(Long areaId) {
        int areaNum = mapper.countByAreaId(areaId);
        if (areaNum > 0) {
            throw new BusinessException(UserBusinessError.USER_00001);
        }
        this.removeById(areaId);
    }

    @Override
    @Cacheable(cacheNames = UserCacheNames.AREA_KEY, key = "'list:' + #pid")
    public List<AreaVO> listByPid(Long pid) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(AREA.PARENT_ID.eq(pid));
        return this.listAs(queryWrapper, AreaVO.class);
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = UserCacheNames.AREA_INFO_KEY, key = "'areaList'"),
            @CacheEvict(cacheNames = UserCacheNames.AREA_KEY, key = "'list:' + #pid")})
    public void removeAllCache(Long pid) {

    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = UserCacheNames.AREA_KEY, key = "'list:' + #pid"),
            @CacheEvict(cacheNames = UserCacheNames.AREA_INFO_KEY, key = "'areaList'")})
    public void removeAreaCacheByParentId(Long pid) {

    }
}
