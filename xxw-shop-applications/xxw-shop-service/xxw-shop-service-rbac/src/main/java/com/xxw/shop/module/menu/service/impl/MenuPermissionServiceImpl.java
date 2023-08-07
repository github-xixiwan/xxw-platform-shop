package com.xxw.shop.module.menu.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.cache.RbacCacheNames;
import com.xxw.shop.constant.RbacBusinessError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.menu.dto.MenuPermissionQueryDTO;
import com.xxw.shop.module.menu.entity.MenuPermission;
import com.xxw.shop.module.menu.entity.table.MenuPermissionTableDef;
import com.xxw.shop.module.menu.entity.table.MenuTableDef;
import com.xxw.shop.module.menu.mapper.MenuPermissionMapper;
import com.xxw.shop.module.menu.service.MenuPermissionService;
import com.xxw.shop.module.menu.vo.MenuPermissionVO;
import com.xxw.shop.module.menu.vo.UriPermissionVO;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.module.web.util.SpringContextUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@Service
public class MenuPermissionServiceImpl extends ServiceImpl<MenuPermissionMapper, MenuPermission> implements MenuPermissionService {

    @Override
    public Page<MenuPermissionVO> page(MenuPermissionQueryDTO dto) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(MenuPermissionTableDef.MENU_PERMISSION.ALL_COLUMNS);
        queryWrapper.select(MenuTableDef.MENU.TITLE);
        queryWrapper.from(MenuPermissionTableDef.MENU_PERMISSION);
        queryWrapper.leftJoin(MenuTableDef.MENU).on(MenuPermissionTableDef.MENU_PERMISSION.MENU_ID.eq(MenuTableDef.MENU.MENU_ID));
        queryWrapper.where(MenuPermissionTableDef.MENU_PERMISSION.BIZ_TYPE.eq(dto.getSysType()));
        queryWrapper.orderBy(MenuPermissionTableDef.MENU_PERMISSION.MENU_PERMISSION_ID.desc());
        return mapper.paginateAs(dto.getPageNumber(), dto.getPageSize(), queryWrapper, MenuPermissionVO.class);
    }

    @Override
    public MenuPermissionVO getByMenuPermissionId(Long menuPermissionId) {
        return mapper.getByMenuPermissionId(menuPermissionId);
    }

    @Override
    @CacheEvict(cacheNames = RbacCacheNames.URI_PERMISSION_KEY, key = "#menuPermission.bizType")
    public ServerResponseEntity<Void> insert(MenuPermission menuPermission) {
        MenuPermission dbMenuPermission = mapper.getByPermission(menuPermission.getPermission(),
                AuthUserContext.get().getSysType());
        if (dbMenuPermission != null) {
            return ServerResponseEntity.fail(RbacBusinessError.RBAC_00001);
        }
        this.save(menuPermission);
        return ServerResponseEntity.success();
    }

    @Override
    @CacheEvict(cacheNames = RbacCacheNames.URI_PERMISSION_KEY, key = "#menuPermission.bizType")
    public ServerResponseEntity<Void> update(MenuPermission menuPermission) {
        MenuPermission dbMenuPermission = mapper.getByPermission(menuPermission.getPermission(),
                AuthUserContext.get().getSysType());
        if (dbMenuPermission != null && !Objects.equals(menuPermission.getMenuPermissionId(),
                dbMenuPermission.getMenuPermissionId())) {
            return ServerResponseEntity.fail(RbacBusinessError.RBAC_00001);
        }
        this.updateById(menuPermission);
        return ServerResponseEntity.success();
    }

    @Override
    @CacheEvict(cacheNames = RbacCacheNames.URI_PERMISSION_KEY, key = "#sysType")
    public void removeById(Long menuPermissionId, Integer sysType) {
        mapper.removeById(menuPermissionId, sysType);
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = RbacCacheNames.USER_PERMISSIONS_KEY, key = "#sysType + ':' + #userId")
            , @CacheEvict(cacheNames = RbacCacheNames.MENU_ID_LIST_KEY, key = "#userId")})
    public void clearUserPermissionsCache(Long userId, Integer sysType) {
    }

    @Override
    public List<String> listUserPermissions(Long userId, Integer sysType, boolean isAdmin) {
        MenuPermissionServiceImpl menuPermissionService = (MenuPermissionServiceImpl) SpringContextUtils.getBean(
                "menuPermissionServiceImpl");
        List<String> permsList;

        // 系统管理员，拥有最高权限
        if (isAdmin) {
            permsList = menuPermissionService.listAllPermission(sysType);
        } else {
            permsList = menuPermissionService.listPermissionByUserIdAndSysType(userId, sysType);
        }
        return permsList;
    }

    @Override
    @Cacheable(cacheNames = RbacCacheNames.URI_PERMISSION_KEY, key = "#sysType")
    public List<UriPermissionVO> listUriPermissionInfo(Integer sysType) {
        return mapper.listUriPermissionInfo(sysType);
    }

    @Override
    public List<MenuPermissionVO> listByMenuId(Long menuId) {
        return mapper.listByMenuId(menuId);
    }

    /**
     * 获取某个类型用户的所有权限列表
     *
     * @param sysType 系统类型
     * @return 权限列表
     */
    public List<String> listAllPermission(Integer sysType) {
        return mapper.listAllPermissionBySysType(sysType);
    }

    /**
     * 获取某个类型用户的所有权限列表（有缓存）
     *
     * @param sysType 系统类型
     * @return 权限列表
     */
    @Cacheable(cacheNames = RbacCacheNames.USER_PERMISSIONS_KEY, key = "#sysType + ':' + #userId")
    public List<String> listPermissionByUserIdAndSysType(Long userId, Integer sysType) {
        return mapper.listPermissionByUserIdAndSysType(userId, sysType);
    }
}
