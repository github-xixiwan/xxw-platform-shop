package com.xxw.shop.feign;

import cn.hutool.core.collection.CollUtil;
import com.xxw.shop.api.rbac.dto.UserRoleDTO;
import com.xxw.shop.api.rbac.feign.UserRoleFeignClient;
import com.xxw.shop.cache.RbacCacheNames;
import com.xxw.shop.module.user.mapper.UserRoleMapper;
import com.xxw.shop.module.web.response.ServerResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRoleFeignController implements UserRoleFeignClient {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = RbacCacheNames.MENU_ID_LIST_KEY, key = "#userRoleDTO.userId")
    public ServerResponseEntity<Void> saveByUserIdAndSysType(UserRoleDTO userRoleDTO) {

        if (CollUtil.isEmpty(userRoleDTO.getRoleIds())) {
            return ServerResponseEntity.success();
        }
        //保存用户与角色关系
        userRoleMapper.insertUserAndUserRole(userRoleDTO.getUserId(), userRoleDTO.getRoleIds());
        return ServerResponseEntity.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = RbacCacheNames.MENU_ID_LIST_KEY, key = "#userRoleDTO.userId")
    public ServerResponseEntity<Void> updateByUserIdAndSysType(UserRoleDTO userRoleDTO) {
        //先删除用户与角色关系
        userRoleMapper.deleteByUserId(userRoleDTO.getUserId());

        if (CollUtil.isEmpty(userRoleDTO.getRoleIds())) {
            return ServerResponseEntity.success();
        }
        //保存用户与角色关系
        userRoleMapper.insertUserAndUserRole(userRoleDTO.getUserId(), userRoleDTO.getRoleIds());
        return ServerResponseEntity.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = RbacCacheNames.MENU_ID_LIST_KEY, key = "#userId")
    public ServerResponseEntity<Void> deleteByUserIdAndSysType(Long userId) {
        userRoleMapper.deleteByUserId(userId);
        return ServerResponseEntity.success();
    }

    @Override
    public ServerResponseEntity<List<Long>> getRoleIds(Long userId) {
        return ServerResponseEntity.success(userRoleMapper.getRoleIds(userId));
    }
}
