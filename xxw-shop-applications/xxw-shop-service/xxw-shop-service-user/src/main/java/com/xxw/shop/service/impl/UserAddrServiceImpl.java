package com.xxw.shop.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.user.vo.UserAddrVO;
import com.xxw.shop.cache.UserCacheNames;
import com.xxw.shop.entity.UserAddr;
import com.xxw.shop.mapper.UserAddrMapper;
import com.xxw.shop.module.common.constant.Constant;
import com.xxw.shop.service.UserAddrService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.xxw.shop.entity.table.UserAddrTableDef.USER_ADDR;

/**
 *  服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-11
 */
@Service
public class UserAddrServiceImpl extends ServiceImpl<UserAddrMapper, UserAddr> implements UserAddrService {

    @Override
    public List<UserAddrVO> lists(Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(USER_ADDR.USER_ID.eq(userId));
        return this.listAs(queryWrapper, UserAddrVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserAddr(UserAddr userAddr) {
        if (userAddr.getIsDefault().equals(Constant.DEFAULT_ADDR)) {
            mapper.removeDefaultUserAddr(userAddr.getUserId());
        }
        this.save(userAddr);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserAddr(UserAddr userAddr) {
        if (userAddr.getIsDefault().equals(Constant.DEFAULT_ADDR)) {
            mapper.removeDefaultUserAddr(userAddr.getUserId());
        }
        this.updateById(userAddr);
    }

    @Override
    public void deleteUserAddrByUserId(Long addrId, Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(USER_ADDR.ADDR_ID.eq(addrId));
        queryWrapper.and(USER_ADDR.USER_ID.eq(userId));
        this.remove(queryWrapper);
    }

    @Override
    public UserAddrVO getUserAddrByUserId(Long addrId, Long userId) {
        // 获取用户默认地址
        if (addrId == 0) {
            return mapper.getUserDefaultAddrByUserId(userId);
        }
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(USER_ADDR.ADDR_ID.eq(addrId));
        queryWrapper.and(USER_ADDR.USER_ID.eq(userId));
        return  this.getOneAs(queryWrapper,UserAddrVO.class);
    }

    @Override
    public long countByUserId(Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.and(USER_ADDR.USER_ID.eq(userId));
        return this.count(queryWrapper);
    }

    @Override
    @CacheEvict(cacheNames = UserCacheNames.USER_DEFAULT_ADDR, key = "#userId")
    public void removeUserDefaultAddrCacheByUserId(Long userId) {

    }
}
