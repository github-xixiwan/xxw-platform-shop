package com.xxw.shop.module.role.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.module.role.dto.RoleQueryDTO;
import com.xxw.shop.module.role.entity.Role;
import com.xxw.shop.module.role.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取角色列表
     *
     * @param dto
     * @return 角色列表
     */
    Page<RoleVO> list(Page<RoleVO> page, @Param("dto") RoleQueryDTO dto);

    /**
     * 获取角色列表
     *
     * @param dto
     * @return 角色列表
     */
    List<RoleVO> list(@Param("dto") RoleQueryDTO dto);

    /**
     * 根据角色id获取角色
     *
     * @param roleId 角色id
     * @return 角色
     */
    RoleVO getByRoleId(@Param("roleId") Long roleId);

    /**
     * 保存角色
     *
     * @param role 角色
     */
    void save(@Param("role") Role role);

    /**
     * 更新角色
     *
     * @param role 角色
     * @return
     */
    void modify(@Param("role") Role role);

    /**
     * 根据角色id删除角色
     *
     * @param roleId  角色id
     * @param sysType 系统类型
     */
    void deleteById(@Param("roleId") Long roleId, @Param("sysType") Integer sysType);

    /**
     * 根据角色id获取该角色所在系统
     *
     * @param roleId 角色id
     * @return sysType
     */
    Integer getBizType(@Param("roleId") Long roleId);

}
