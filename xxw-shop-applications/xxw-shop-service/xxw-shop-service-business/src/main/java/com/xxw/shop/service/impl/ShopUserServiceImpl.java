package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.auth.dto.AuthAccountDTO;
import com.xxw.shop.api.auth.feign.AccountFeignClient;
import com.xxw.shop.api.auth.vo.AuthAccountVO;
import com.xxw.shop.api.rbac.dto.UserRoleDTO;
import com.xxw.shop.api.rbac.feign.UserRoleFeignClient;
import com.xxw.shop.dto.ChangeAccountDTO;
import com.xxw.shop.dto.ShopUserQueryDTO;
import com.xxw.shop.entity.ShopUser;
import com.xxw.shop.mapper.ShopUserMapper;
import com.xxw.shop.module.common.bo.UserInfoInTokenBO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.module.web.util.IpHelper;
import com.xxw.shop.service.ShopUserService;
import com.xxw.shop.vo.ShopUserVO;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.xxw.shop.entity.table.ShopUserTableDef.SHOP_USER;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
@Service
public class ShopUserServiceImpl extends ServiceImpl<ShopUserMapper, ShopUser> implements ShopUserService {

    @Resource
    private UserRoleFeignClient userRoleFeignClient;

    @Resource
    private AccountFeignClient accountFeignClient;


    @Override
    public Page<ShopUserVO> page(ShopUserQueryDTO dto) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_USER.SHOP_ID.eq(dto.getShopId()));
        queryWrapper.and(SHOP_USER.NICK_NAME.like(dto.getNickName()));
        queryWrapper.orderBy(SHOP_USER.SHOP_USER_ID.desc());
        return this.pageAs(new Page<>(dto.getPageNumber(), dto.getPageSize()), queryWrapper, ShopUserVO.class);
    }

    @Override
    public ShopUserVO getByUserId(Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_USER.SHOP_USER_ID.eq(userId));
        ShopUserVO shopUser = this.getOneAs(queryWrapper, ShopUserVO.class);
        ServerResponseEntity<List<Long>> roleIds = userRoleFeignClient.getRoleIds(shopUser.getShopUserId());
        shopUser.setRoleIds(roleIds.getData());
        return shopUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveShopUser(ShopUser shopUser, List<Long> roleIds) {
        this.save(shopUser);
        if (CollUtil.isEmpty(roleIds)) {
            return;
        }
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setRoleIds(roleIds);
        userRoleDTO.setUserId(shopUser.getShopUserId());
        userRoleFeignClient.saveByUserIdAndSysType(userRoleDTO);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public void updateShopUser(ShopUser shopUser, List<Long> roleIds) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setRoleIds(roleIds);
        userRoleDTO.setUserId(shopUser.getShopUserId());
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_USER.SHOP_USER_ID.eq(shopUser.getShopUserId()));
        queryWrapper.and(SHOP_USER.SHOP_ID.eq(shopUser.getShopId()));
        this.update(shopUser, queryWrapper);
        userRoleFeignClient.updateByUserIdAndSysType(userRoleDTO);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long shopUserId) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        accountFeignClient.deleteByUserIdAndSysType(shopUserId);
        userRoleFeignClient.deleteByUserIdAndSysType(shopUserId);
        this.removeById(shopUserId);
    }

    @Override
    public Long getUserIdByShopId(Long shopId) {
        return mapper.getUserIdByShopId(shopId);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseEntity<Void> saveChangeAccount(ChangeAccountDTO changeAccountDTO) {
        AuthAccountDTO authAccountDTO = getAuthAccountDTO(changeAccountDTO);
        authAccountDTO.setCreateIp(IpHelper.getIpAddr());
        authAccountDTO.setIsAdmin(0);
        // 保存
        ServerResponseEntity<Long> serverResponseEntity = accountFeignClient.save(authAccountDTO);
        if (!serverResponseEntity.isSuccess()) {
            return ServerResponseEntity.transform(serverResponseEntity);
        }
        ShopUser shopUser = new ShopUser();
        shopUser.setShopUserId(changeAccountDTO.getUserId());
        shopUser.setHasAccount(1);
        shopUser.setShopId(AuthUserContext.get().getTenantId());

        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_USER.SHOP_USER_ID.eq(shopUser.getShopUserId()));
        queryWrapper.and(SHOP_USER.SHOP_ID.eq(shopUser.getShopId()));
        this.update(shopUser, queryWrapper);
        return ServerResponseEntity.success();
    }

    @Override
    public ServerResponseEntity<Void> updateChangeAccount(ChangeAccountDTO changeAccountDTO) {
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
