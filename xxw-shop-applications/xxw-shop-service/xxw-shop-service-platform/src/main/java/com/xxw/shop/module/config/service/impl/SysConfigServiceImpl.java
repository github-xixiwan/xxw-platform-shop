package com.xxw.shop.module.config.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.cache.PlatformCacheNames;
import com.xxw.shop.module.config.entity.SysConfig;
import com.xxw.shop.module.config.mapper.SysConfigMapper;
import com.xxw.shop.module.config.service.SysConfigService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.xxw.shop.module.config.entity.table.SysConfigTableDef.SYS_CONFIG;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = PlatformCacheNames.SYS_CONFIG_OBJECT, key = "#key"),
            @CacheEvict(cacheNames = PlatformCacheNames.SYS_CONFIG, key = "#key")})
    public void modifyValueByKey(String key, String value) {

    }

    @Override
    public void deleteBatch(Long[] ids) {

    }

    @Override
    @Cacheable(cacheNames = PlatformCacheNames.SYS_CONFIG, key = "#key")
    public String getValue(String key) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SYS_CONFIG.PARAM_KEY.eq(key));
        SysConfig sysConfig = this.getOne(queryWrapper);
        return sysConfig == null ? null : sysConfig.getParamValue();
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = PlatformCacheNames.SYS_CONFIG_OBJECT, key = "#key"),
            @CacheEvict(cacheNames = PlatformCacheNames.SYS_CONFIG, key = "#key")})
    public void removeSysConfig(String key) {
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = PlatformCacheNames.SYS_CONFIG_OBJECT, key = "#sysConfig.paramKey"),
            @CacheEvict(cacheNames = PlatformCacheNames.SYS_CONFIG, key = "#sysConfig.paramKey")})
    public void saveOrUpdateSysConfig(SysConfig sysConfig) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SYS_CONFIG.PARAM_KEY.eq(sysConfig.getParamKey()));
        if (this.count(queryWrapper) > 0) {
            this.update(sysConfig, queryWrapper);
        } else {
            this.save(sysConfig);
        }
    }

    @Override
    @Cacheable(cacheNames = PlatformCacheNames.SYS_CONFIG, key = "#key")
    public SysConfig getByKey(String key) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SYS_CONFIG.PARAM_KEY.eq(key));
        return this.getOne(queryWrapper);
    }

    @Override
    @Cacheable(cacheNames = PlatformCacheNames.SYS_CONFIG_OBJECT, key = "#key")
    public <T> T getSysConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        if (Objects.equals(String.class, clazz)) {
            return clazz.cast(value);
        } else {
            return JSONUtil.toBean(value, clazz);
        }
    }
}
