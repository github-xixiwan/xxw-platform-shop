package com.xxw.shop.module.user.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.api.auth.vo.AuthAccountVO;
import com.xxw.shop.module.user.dto.ChangeAccountDTO;
import com.xxw.shop.module.user.dto.SysUserQueryDTO;
import com.xxw.shop.module.user.entity.SysUser;
import com.xxw.shop.module.user.vo.SysUserSimpleVO;
import com.xxw.shop.module.user.vo.SysUserVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;

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
    void modify(SysUser sysUser, List<Long> roleIds);

    /**
     * 根据平台用户id删除平台用户信息
     *
     * @param sysUserId 平台用户id
     */
    void deleteById(Long sysUserId);

    /**
     * 添加账户
     *
     * @param changeAccountDTO 账户信息
     * @return void
     */
    ServerResponseEntity<Void> save(ChangeAccountDTO changeAccountDTO);

    /**
     * 更新账户
     *
     * @param changeAccountDTO 账户信息
     * @return
     */
    ServerResponseEntity<Void> updateAccount(ChangeAccountDTO changeAccountDTO);

    /**
     * 根据用户id和系统类型获取用户信息
     *
     * @param userId  用户id
     * @param sysType 系统类型
     * @return void
     */
    ServerResponseEntity<AuthAccountVO> getByUserIdAndSysType(Long userId, Integer sysType);
}
