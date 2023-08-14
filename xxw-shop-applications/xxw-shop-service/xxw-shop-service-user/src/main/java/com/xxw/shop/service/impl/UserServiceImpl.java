package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.auth.dto.AuthAccountDTO;
import com.xxw.shop.api.auth.feign.AccountFeignClient;
import com.xxw.shop.api.auth.vo.AuthAccountVO;
import com.xxw.shop.api.user.vo.UserCompleteVO;
import com.xxw.shop.cache.UserCacheNames;
import com.xxw.shop.constant.UserBusinessError;
import com.xxw.shop.dto.UserQueryDTO;
import com.xxw.shop.dto.UserRegisterDTO;
import com.xxw.shop.entity.User;
import com.xxw.shop.mapper.UserMapper;
import com.xxw.shop.module.common.constant.SysTypeEnum;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.util.IpHelper;
import com.xxw.shop.service.UserService;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.xxw.shop.entity.table.UserTableDef.USER;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private AccountFeignClient accountFeignClient;

    @Override
    public Page<UserCompleteVO> page(UserQueryDTO dto) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.orderBy(USER.USER_ID.desc());
        return this.pageAs(new Page<>(dto.getPageNumber(), dto.getPageSize()), queryWrapper, UserCompleteVO.class);
    }

    @Override
    @Cacheable(cacheNames = UserCacheNames.USER_INFO, key = "#userId")
    public UserCompleteVO getByUserId(Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(USER.USER_ID.eq(userId));
        User user = this.getById(userId);
        return mapperFacade.map(user, UserCompleteVO.class);
    }

    @Override
    @CacheEvict(cacheNames = UserCacheNames.USER_INFO, key = "#user.userId")
    public void updateUser(User user) {
        this.updateById(user);
    }

    @Override
    public List<UserCompleteVO> getUserByUserIds(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(USER.USER_ID.in(userIds));
        return listAs(queryWrapper, UserCompleteVO.class);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public Long saveUser(UserRegisterDTO param) {
        this.checkRegisterInfo(param);
        //TODO
        Long userId = System.currentTimeMillis();

        param.setUserId(userId);

        AuthAccountDTO authAccountDTO = new AuthAccountDTO();
        authAccountDTO.setCreateIp(IpHelper.getIpAddr());
        authAccountDTO.setPassword(param.getPassword());
        authAccountDTO.setIsAdmin(0);
        authAccountDTO.setSysType(SysTypeEnum.ORDINARY.value());
        authAccountDTO.setUsername(param.getUserName());
        authAccountDTO.setStatus(1);
        authAccountDTO.setUserId(userId);

        // 保存统一账户信息
        ServerResponseEntity<Long> serverResponse = accountFeignClient.save(authAccountDTO);
        // 抛异常回滚
        if (!serverResponse.isSuccess()) {
            throw new BusinessException(serverResponse.getMessage());
        }

        User user = new User();
        user.setUserId(userId);
        user.setPic(param.getImg());
        user.setNickName(param.getNickName());
        user.setStatus(1);
        // 这里保存之后才有用户id
        this.save(user);

        return serverResponse.getData();
    }

    private void checkRegisterInfo(UserRegisterDTO userRegisterDTO) {
        ServerResponseEntity<AuthAccountVO> responseEntity =
                accountFeignClient.getByUsernameAndSysType(userRegisterDTO.getUserName(), SysTypeEnum.ORDINARY);
        if (!responseEntity.isSuccess()) {
            throw new BusinessException(responseEntity.getMessage());
        }
        if (Objects.nonNull(responseEntity.getData())) {
            throw new BusinessException(UserBusinessError.USER_00002);
        }
    }
}
