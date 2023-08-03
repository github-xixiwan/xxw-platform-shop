package com.xxw.shop.module.user.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.module.user.dto.SysUserQueryDTO;
import com.xxw.shop.module.user.entity.SysUser;
import com.xxw.shop.module.user.vo.SysUserSimpleVO;
import com.xxw.shop.module.user.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户id获取当前登陆的商家用户信息
     *
     * @param userId 用户id
     * @return 商家用户信息
     */
    SysUserSimpleVO getSimpleByUserId(@Param("userId") Long userId);

    /**
     * 获取平台用户列表
     *
     * @param dto
     * @return 平台用户列表
     */
    Page<SysUserVO> listByShopId(Page<SysUserVO> page, @Param("dto") SysUserQueryDTO dto);

    /**
     * 根据用户id获取商家用户信息
     *
     * @param userId 用户id
     * @return 商家用户信息
     */
    SysUserVO getByUserId(@Param("userId") Long userId);

    /**
     * 保存商家用户信息
     *
     * @param sysUser
     */
    void save(@Param("sysUser") SysUser sysUser);

    /**
     * 更新平台用户信息
     *
     * @param sysUser
     * @return
     */
    void modify(@Param("sysUser") SysUser sysUser);

    /**
     * 根据平台用户id删除平台用户
     *
     * @param sysUserId
     */
    void deleteById(@Param("sysUserId") Long sysUserId);
}
