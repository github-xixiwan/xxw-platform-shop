package com.xxw.shop.module.role.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.module.role.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 批量插入角色菜单关联关系
     *
     * @param roleMenus 角色菜单对象列表
     * @return
     */
//    int insertBatch(@Param("roleMenus") List<RoleMenu> roleMenus);

    /**
     * 根据角色id删除角色与菜单的关联关系
     *
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色id，获取角色菜单关联关系列表
     *
     * @param roleId 角色id
     * @return 角色菜单关联关系列表
     */
    List<RoleMenu> getByRoleId(@Param("roleId") Long roleId);
}
