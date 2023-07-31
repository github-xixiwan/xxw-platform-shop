package com.xxw.shop.module.user.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.module.user.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户id删除用户与角色关系
     *
     * @param userId
     */
    void deleteByUserId(Long userId);

    /**
     * 根据用户id 批量添加用户角色关系
     *
     * @param userId
     * @param roleIdList
     */
    void insertUserAndUserRole(@Param("userId") Long userId, @Param("roleIdList") List<Long> roleIdList);

    /**
     * 根据用户id 获取用户角色关系
     *
     * @param userId 用户id
     * @return 角色id列表
     */
    List<Long> getRoleIds(Long userId);

    /**
     * 根据角色id 删除用户角色关系
     *
     * @param roleId 用户id
     */
    void deleteByRoleId(Long roleId);
}
