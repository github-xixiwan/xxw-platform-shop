package com.xxw.shop.module.user.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.module.user.dto.UserQueryDTO;
import com.xxw.shop.module.user.entity.SysUser;
import com.xxw.shop.module.user.mapper.SysUserMapper;
import com.xxw.shop.module.user.service.SysUserService;
import com.xxw.shop.module.user.vo.SysUserSimpleVO;
import com.xxw.shop.module.user.vo.SysUserVO;
import com.xxw.shop.module.web.cache.CacheNames;
import com.xxw.shop.module.web.page.PageDTO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  服务层实现。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    @Cacheable(cacheNames = CacheNames.SERVICE_PLATFORM_SYS_USER_KEY, key = "#userId")
    public SysUserSimpleVO getSimpleByUserId(Long userId) {
        return sysUserMapper.getSimpleByUserId(userId);
    }

    @Override
    public Page<SysUserVO> pageByShopId(UserQueryDTO dto) {
        return PageUtil.doPage(pageDTO, () -> sysUserMapper.listByShopId(shopId, nickName));
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
        sysUserMapper.save(sysUser);
        userRoleDTO.setUserId(sysUser.getSysUserId());
        userRoleFeignClient.saveByUserIdAndSysType(userRoleDTO);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CacheNames.SERVICE_PLATFORM_SYS_USER_KEY, key = "#sysUser.sysUserId")
    public void update(SysUser sysUser, List<Long> roleIds) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setRoleIds(roleIds);
        userRoleDTO.setUserId(sysUser.getSysUserId());
        sysUserMapper.update(sysUser);
        userRoleFeignClient.updateByUserIdAndSysType(userRoleDTO);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CacheNames.SERVICE_PLATFORM_SYS_USER_KEY, key = "#sysUserId")
    public void deleteById(Long sysUserId) {
        accountFeignClient.deleteByUserIdAndSysType(sysUserId);
        userRoleFeignClient.deleteByUserIdAndSysType(sysUserId);
        sysUserMapper.deleteById(sysUserId);
    }
}
