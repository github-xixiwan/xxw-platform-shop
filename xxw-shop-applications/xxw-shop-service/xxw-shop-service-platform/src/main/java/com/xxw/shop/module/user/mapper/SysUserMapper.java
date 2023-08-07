package com.xxw.shop.module.user.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.module.user.dto.SysUserQueryDTO;
import com.xxw.shop.module.user.entity.SysUser;
import com.xxw.shop.module.user.vo.SysUserSimpleVO;
import com.xxw.shop.module.user.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<SysUserVO> listByShopId(@Param("dto") SysUserQueryDTO dto);
}
