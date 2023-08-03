package com.xxw.shop.module.config.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.cache.PlatformCacheNames;
import com.xxw.shop.module.config.entity.SysConfig;
import com.xxw.shop.module.config.mapper.SysConfigMapper;
import com.xxw.shop.module.config.service.SysConfigService;
import com.xxw.shop.module.common.json.JsonUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
//        mapper.modifyValueByKey(key, value);
    }

    @Override
    public void deleteBatch(Long[] ids) {
//        mapper.deleteBatch(ids);
    }

    @Override
    @Cacheable(cacheNames = PlatformCacheNames.SYS_CONFIG, key = "#key")
    public String getValue(String key) {
        SysConfig config = mapper.queryByKey(key);
        return config == null ? null : config.getParamValue();
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
        if (mapper.countByKey(sysConfig.getParamKey()) > 0) {
            mapper.modify(sysConfig);
        } else {
            mapper.save(sysConfig);
        }
    }

    @Override
    @Cacheable(cacheNames = PlatformCacheNames.SYS_CONFIG, key = "#key")
    public SysConfig getByKey(String key) {
        return mapper.queryByKey(key);
    }


    @Override
    @Cacheable(cacheNames = PlatformCacheNames.SYS_CONFIG_OBJECT, key = "#key")
    public <T> T getSysConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StrUtil.isBlank(value)) {
            return null;
        }

        if (Objects.equals(String.class, clazz)) {
            return (T) value;
        } else {
            return JsonUtil.fromJson(value, clazz);
        }
    }
}
