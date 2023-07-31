package com.xxw.shop.module.role.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.module.role.dto.RoleQueryDTO;
import com.xxw.shop.module.role.entity.Role;
import com.xxw.shop.module.role.vo.RoleVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public interface RoleService extends IService<Role> {

    /**
     * 分页获取角色列表
     *
     * @param dto 参数
     * @return 角色列表分页数据
     */
    Page<RoleVO> page(RoleQueryDTO dto);

    /**
     * 分页获取角色列表
     *
     * @param sysType  系统类型
     * @param tenantId 租户id
     * @return 角色列表分页数据
     */
    List<RoleVO> list(Integer sysType, Long tenantId);

    /**
     * 根据角色id获取角色
     *
     * @param roleId 角色id
     * @return 角色
     */
    RoleVO getByRoleId(Long roleId);

    /**
     * 保存角色
     *
     * @param role              角色
     * @param menuIds           菜单id列表
     * @param menuPermissionIds 权限id列表
     */
    void save(Role role, List<Long> menuIds, List<Long> menuPermissionIds);

    /**
     * 更新角色
     *
     * @param role              角色
     * @param menuIds           菜单id列表
     * @param menuPermissionIds 权限id列表
     */
    void update(Role role, List<Long> menuIds, List<Long> menuPermissionIds);

    /**
     * 根据角色id删除角色
     *
     * @param roleId
     * @param sysType
     */
    void deleteById(Long roleId, Integer sysType);

    /**
     * 根据角色id获取该角色所在系统
     *
     * @param roleId 角色id
     * @return sysType
     */
    Integer getBizType(Long roleId);
}
