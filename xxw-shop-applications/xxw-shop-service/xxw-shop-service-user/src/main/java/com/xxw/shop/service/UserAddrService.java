package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.api.user.vo.UserAddrVO;
import com.xxw.shop.entity.UserAddr;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-11
 */
public interface UserAddrService extends IService<UserAddr> {

    List<UserAddrVO> lists(Long userId);

    void saveUserAddr(UserAddr userAddr);

    void updateUserAddr(UserAddr userAddr);

    void deleteUserAddrByUserId(Long addrId, Long userId);

    UserAddrVO getUserAddrByUserId(Long addrId, Long userId);

    long countByUserId(Long userId);

    void removeUserDefaultAddrCacheByUserId(Long userId);
}
