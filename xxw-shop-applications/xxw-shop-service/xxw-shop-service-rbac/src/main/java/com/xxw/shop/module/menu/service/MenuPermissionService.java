package com.xxw.shop.module.menu.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.module.menu.dto.MenuPermissionQueryDTO;
import com.xxw.shop.module.menu.vo.UriPermissionVO;
import com.xxw.shop.module.menu.entity.MenuPermission;
import com.xxw.shop.module.menu.vo.MenuPermissionVO;
import com.xxw.shop.module.web.response.ServerResponseEntity;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public interface MenuPermissionService extends IService<MenuPermission> {

    /**
     * 分页获取菜单资源列表
     *
     * @param dto 参数
     * @return 菜单资源列表分页数据
     */
    Page<MenuPermissionVO> page(MenuPermissionQueryDTO dto);

    /**
     * 根据菜单资源id获取菜单资源
     *
     * @param menuPermissionId 菜单资源id
     * @return 菜单资源
     */
    MenuPermissionVO getByMenuPermissionId(Long menuPermissionId);

    /**
     * 保存菜单资源
     *
     * @param menuPermission 菜单资源
     * @return
     */
    boolean save(MenuPermission menuPermission);

    /**
     * 更新菜单资源
     *
     * @param menuPermission 菜单资源
     * @return
     */
    ServerResponseEntity<Void> update(MenuPermission menuPermission);

    /**
     * 根据菜单资源id删除菜单资源
     *
     * @param menuPermissionId
     * @param sysType
     */
    void deleteById(Long menuPermissionId, Integer sysType);

    /**
     * 根据用户所在系统的用户id，用户所在系统类型，获取用户的权限列表
     *
     * @param userId  用户id
     * @param sysType 系统类型
     * @param isAdmin 是否为管理员
     * @return 权限列表
     */
    List<String> listUserPermissions(Long userId, Integer sysType, boolean isAdmin);

    /**
     * 清除用户权限缓存
     *
     * @param userId  用户id
     * @param sysType 系统类型
     */
    void clearUserPermissionsCache(Long userId, Integer sysType);

    /**
     * 根据系统类型，获取该类型用户拥有的所有权限数据
     *
     * @param sysType 系统类型
     * @return uri权限列表
     */
    List<UriPermissionVO> listUriPermissionInfo(Integer sysType);

    /**
     * 根据menuId获取菜单资源列表
     *
     * @param menuId 菜单id
     * @return 菜单资源列表数据
     */
    List<MenuPermissionVO> listByMenuId(Long menuId);

}
