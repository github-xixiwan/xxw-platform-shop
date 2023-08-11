package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.api.user.vo.UserAddrVO;
import com.xxw.shop.entity.UserAddr;
import org.apache.ibatis.annotations.Param;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-11
 */
public interface UserAddrMapper extends BaseMapper<UserAddr> {

    /**
     * 移除用户默认地址
     *
     * @param userId
     */
    void removeDefaultUserAddr(@Param("userId") Long userId);

    /**
     * 通过用户id获取默认地址
     *
     * @param userId 用户id
     * @return 默认地址
     */
    UserAddrVO getUserDefaultAddrByUserId(@Param("userId") Long userId);
}
