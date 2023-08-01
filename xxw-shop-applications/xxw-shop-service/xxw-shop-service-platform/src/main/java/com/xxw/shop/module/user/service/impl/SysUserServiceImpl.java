package com.xxw.shop.module.user.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.auth.feign.AccountFeignClient;
import com.xxw.shop.api.rbac.dto.UserRoleDTO;
import com.xxw.shop.api.rbac.feign.UserRoleFeignClient;
import com.xxw.shop.cache.PlatformCacheNames;
import com.xxw.shop.module.user.dto.SysUserQueryDTO;
import com.xxw.shop.module.user.entity.SysUser;
import com.xxw.shop.module.user.mapper.SysUserMapper;
import com.xxw.shop.module.user.service.SysUserService;
import com.xxw.shop.module.user.vo.SysUserSimpleVO;
import com.xxw.shop.module.user.vo.SysUserVO;
import com.xxw.shop.module.web.response.ServerResponseEntity;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private AccountFeignClient accountFeignClient;

    @Resource
    private UserRoleFeignClient userRoleFeignClient;

    @Override
    @Cacheable(cacheNames = PlatformCacheNames.PLATFORM_SIMPLE_INFO_KEY, key = "#userId")
    public SysUserSimpleVO getSimpleByUserId(Long userId) {
        return mapper.getSimpleByUserId(userId);
    }

    @Override
    public Page<SysUserVO> pageByShopId(SysUserQueryDTO dto) {
        return mapper.listByShopId(new Page<>(dto.getPageNumber(), dto.getPageSize()), dto);
    }

    @Override
    public SysUserVO getByUserId(Long userId) {
        SysUserVO sysUser = mapper.getByUserId(userId);
        ServerResponseEntity<List<Long>> roleIds = userRoleFeignClient.getRoleIds(sysUser.getSysUserId());
        sysUser.setRoleIds(roleIds.getData());
        return sysUser;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUser sysUser, List<Long> roleIds) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setRoleIds(roleIds);
        mapper.save(sysUser);
        userRoleDTO.setUserId(sysUser.getSysUserId());
        userRoleFeignClient.saveByUserIdAndSysType(userRoleDTO);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = PlatformCacheNames.PLATFORM_SIMPLE_INFO_KEY, key = "#sysUser.sysUserId")
    public void update(SysUser sysUser, List<Long> roleIds) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setRoleIds(roleIds);
        userRoleDTO.setUserId(sysUser.getSysUserId());
        mapper.update(sysUser);
        userRoleFeignClient.updateByUserIdAndSysType(userRoleDTO);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = PlatformCacheNames.PLATFORM_SIMPLE_INFO_KEY, key = "#sysUserId")
    public void deleteById(Long sysUserId) {
        accountFeignClient.deleteByUserIdAndSysType(sysUserId);
        userRoleFeignClient.deleteByUserIdAndSysType(sysUserId);
        mapper.deleteById(sysUserId);
    }
}
