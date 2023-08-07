package com.xxw.shop.module.user.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.auth.dto.AuthAccountDTO;
import com.xxw.shop.api.auth.feign.AccountFeignClient;
import com.xxw.shop.api.auth.vo.AuthAccountVO;
import com.xxw.shop.api.rbac.dto.UserRoleDTO;
import com.xxw.shop.api.rbac.feign.UserRoleFeignClient;
import com.xxw.shop.cache.PlatformCacheNames;
import com.xxw.shop.module.common.bo.UserInfoInTokenBO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.module.user.dto.ChangeAccountDTO;
import com.xxw.shop.module.user.dto.SysUserQueryDTO;
import com.xxw.shop.module.user.entity.SysUser;
import com.xxw.shop.module.user.entity.table.SysUserTableDef;
import com.xxw.shop.module.user.mapper.SysUserMapper;
import com.xxw.shop.module.user.service.SysUserService;
import com.xxw.shop.module.user.vo.SysUserSimpleVO;
import com.xxw.shop.module.user.vo.SysUserVO;
import com.xxw.shop.module.web.util.IpHelper;
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
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.orderBy(SysUserTableDef.SYS_USER.SYS_USER_ID.desc());
        return mapper.paginateAs(dto.getPageNumber(), dto.getPageSize(), queryWrapper, SysUserVO.class);
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
    public void modify(SysUser sysUser, List<Long> roleIds) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setRoleIds(roleIds);
        userRoleDTO.setUserId(sysUser.getSysUserId());
        mapper.modify(sysUser);
        userRoleFeignClient.updateByUserIdAndSysType(userRoleDTO);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = PlatformCacheNames.PLATFORM_SIMPLE_INFO_KEY, key = "#sysUserId")
    public void removeById(Long sysUserId) {
        accountFeignClient.deleteByUserIdAndSysType(sysUserId);
        userRoleFeignClient.deleteByUserIdAndSysType(sysUserId);
        mapper.removeById(sysUserId);
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseEntity<Void> save(ChangeAccountDTO changeAccountDTO) {
        AuthAccountDTO authAccountDTO = getAuthAccountDTO(changeAccountDTO);
        authAccountDTO.setCreateIp(IpHelper.getIpAddr());
        authAccountDTO.setIsAdmin(0);
        // 保存
        ServerResponseEntity<Long> serverResponseEntity = accountFeignClient.save(authAccountDTO);
        if (!serverResponseEntity.isSuccess()) {
            return ServerResponseEntity.transform(serverResponseEntity);
        }
        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(changeAccountDTO.getUserId());
        sysUser.setHasAccount(1);
        mapper.modify(sysUser);
        return ServerResponseEntity.success();
    }

    @Override
    public ServerResponseEntity<Void> update(ChangeAccountDTO changeAccountDTO) {

        AuthAccountDTO authAccountDTO = getAuthAccountDTO(changeAccountDTO);
        // 更新，不涉及分布式事务
        ServerResponseEntity<Void> serverResponseEntity = accountFeignClient.update(authAccountDTO);
        if (!serverResponseEntity.isSuccess()) {
            return serverResponseEntity;
        }

        return ServerResponseEntity.success();
    }

    @Override
    public ServerResponseEntity<AuthAccountVO> getByUserIdAndSysType(Long userId, Integer sysType) {
        return accountFeignClient.getByUserIdAndSysType(userId, sysType);
    }

    private AuthAccountDTO getAuthAccountDTO(ChangeAccountDTO changeAccountDTO) {
        AuthAccountDTO authAccountDTO = new AuthAccountDTO();
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        authAccountDTO.setPassword(changeAccountDTO.getPassword());
        authAccountDTO.setUsername(changeAccountDTO.getUsername());
        authAccountDTO.setStatus(changeAccountDTO.getStatus());
        authAccountDTO.setSysType(userInfoInTokenBO.getSysType());
        authAccountDTO.setTenantId(userInfoInTokenBO.getTenantId());
        authAccountDTO.setUserId(changeAccountDTO.getUserId());
        return authAccountDTO;
    }
}
