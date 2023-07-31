package com.xxw.shop.module.menu.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.module.menu.entity.Menu;
import com.xxw.shop.module.menu.mapper.MenuMapper;
import com.xxw.shop.module.menu.service.MenuService;
import com.xxw.shop.module.menu.vo.MenuSimpleVO;
import com.xxw.shop.module.menu.vo.MenuVO;
import com.xxw.shop.module.web.cache.CacheNames;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public MenuVO getByMenuId(Long menuId) {
        return mapper.getByMenuId(menuId);
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.SERVICE_RBAC_MENU_BIZ_TYPE_KEY, key = "#menu.bizType")
    public boolean save(Menu menu) {
        mapper.save(menu);
        return true;
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.SERVICE_RBAC_MENU_BIZ_TYPE_KEY, key = "#menu.bizType")
    public void update(Menu menu) {
        mapper.update(menu);
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.SERVICE_RBAC_MENU_SYS_TYPE_KEY, key = "#sysType")
    public void deleteById(Long menuId, Integer sysType) {
        mapper.deleteById(menuId, sysType);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.SERVICE_RBAC_MENU_SYS_TYPE_KEY, key = "#sysType")
    public List<Menu> listBySysType(Integer sysType) {
        return mapper.listBySysType(sysType);
    }

    @Override
    public List<MenuSimpleVO> listWithPermissions(Integer sysType) {
        return mapper.listWithPermissions(sysType);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.SERVICE_RBAC_MENU_USER_ID_KEY, key = "#userId")
    public List<Long> listMenuIds(Long userId) {
        return mapper.listMenuIds(userId);
    }
}
