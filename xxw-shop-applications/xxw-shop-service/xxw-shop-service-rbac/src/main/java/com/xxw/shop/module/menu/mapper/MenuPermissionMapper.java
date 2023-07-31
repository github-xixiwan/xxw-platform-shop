package com.xxw.shop.module.menu.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.module.menu.dto.MenuPermissionQueryDTO;
import com.xxw.shop.module.menu.vo.UriPermissionVO;
import com.xxw.shop.module.menu.entity.MenuPermission;
import com.xxw.shop.module.menu.vo.MenuPermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public interface MenuPermissionMapper extends BaseMapper<MenuPermission> {

    /**
     * 获取菜单资源列表
     *
     * @param dto
     * @return 菜单资源列表
     */
    Page<MenuPermissionVO> list(Page<MenuPermissionVO> page, @Param("dto") MenuPermissionQueryDTO dto);

    /**
     * 根据菜单资源id获取菜单资源
     *
     * @param menuPermissionId 菜单资源id
     * @return 菜单资源
     */
    MenuPermissionVO getByMenuPermissionId(@Param("menuPermissionId") Long menuPermissionId);

    /**
     * 保存菜单资源
     *
     * @param menuPermission 菜单资源
     */
    void save(@Param("menuPermission") MenuPermission menuPermission);

    /**
     * 更新菜单资源
     *
     * @param menuPermission 菜单资源
     * @return
     */
    int update(@Param("menuPermission") MenuPermission menuPermission);

    /**
     * 根据菜单资源id删除菜单资源
     *
     * @param menuPermissionId
     * @param sysType
     */
    void deleteById(@Param("menuPermissionId") Long menuPermissionId, @Param("sysType") Integer sysType);

    /**
     * 获取某个类型用户的所有权限列表
     *
     * @param sysType 系统类型
     * @return 权限列表
     */
    List<String> listAllPermissionBySysType(@Param("sysType") Integer sysType);

    /**
     * 获取某个用户的权限列表
     *
     * @param userId  用户id
     * @param sysType 系统类型
     * @return 权限列表
     */
    List<String> listPermissionByUserIdAndSysType(@Param("userId") Long userId, @Param("sysType") Integer sysType);

    /**
     * 根据系统类型，获取该类型用户拥有的所有权限数据
     *
     * @param sysType 系统类型
     * @return uri权限列表
     */
    List<UriPermissionVO> listUriPermissionInfo(@Param("sysType") Integer sysType);

    /**
     * 根据menuId获取菜单资源列表
     *
     * @param menuId 菜单id
     * @return 菜单资源列表数据
     */
    List<MenuPermissionVO> listByMenuId(@Param("menuId") Long menuId);

    /**
     * 通过权限对应的编码获取权限信息
     *
     * @param permission 权限对应的编码
     * @param sysType    系统类型
     * @return
     */
    MenuPermission getByPermission(@Param("permission") String permission, @Param("sysType") Integer sysType);

}
