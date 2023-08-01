package com.xxw.shop.module.user.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.module.user.dto.SysUserQueryDTO;
import com.xxw.shop.module.user.entity.SysUser;
import com.xxw.shop.module.user.vo.SysUserSimpleVO;
import com.xxw.shop.module.user.vo.SysUserVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 根据用户id获取当前登陆的商家用户信息
     *
     * @param userId 用户id
     * @return 商家用户信息
     */
    SysUserSimpleVO getSimpleByUserId(Long userId);

    /**
     * 分页获取平台用户列表
     *
     * @param dto 参数
     * @return 平台用户列表
     */
    Page<SysUserVO> pageByShopId(SysUserQueryDTO dto);

    /**
     * 根据用户id获取商家用户信息
     *
     * @param userId 用户id
     * @return 商家用户信息
     */
    SysUserVO getByUserId(Long userId);

    /**
     * 保存平台用户信息
     *
     * @param sysUser 平台用户id
     * @param roleIds 角色id列表
     */
    void save(SysUser sysUser, List<Long> roleIds);

    /**
     * 更新平台用户信息
     *
     * @param sysUser 平台用户id
     * @param roleIds 角色id列表
     */
    void update(SysUser sysUser, List<Long> roleIds);

    /**
     * 根据平台用户id删除平台用户信息
     *
     * @param sysUserId 平台用户id
     */
    void deleteById(Long sysUserId);
}
