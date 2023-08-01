package com.xxw.shop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.entity.AuthAccount;
import com.xxw.shop.mapper.AuthAccountMapper;
import com.xxw.shop.module.security.bo.AuthAccountInVerifyBO;
import com.xxw.shop.module.security.bo.UserInfoInTokenBO;
import com.xxw.shop.module.security.constant.InputUserNameEnum;
import com.xxw.shop.module.util.string.PrincipalUtil;
import com.xxw.shop.module.web.response.ServerResponseEntity;
import com.xxw.shop.service.AuthAccountService;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 *  服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
@Service
public class AuthAccountServiceImpl extends ServiceImpl<AuthAccountMapper, AuthAccount> implements AuthAccountService {

    public static final String USER_NOT_FOUND_SECRET = "USER_NOT_FOUND_SECRET";

    private static String userNotFoundEncodedPassword;

    @Resource
    private MapperFacade mapperFacade;

    @Override
    public ServerResponseEntity<UserInfoInTokenBO> getUserInfoInTokenByInputUserNameAndPassword(String inputUserName,
                                                                                                String password, Integer sysType) {

        if (StrUtil.isBlank(inputUserName)) {
            return ServerResponseEntity.showFailMsg("用户名不能为空");
        }
        if (StrUtil.isBlank(password)) {
            return ServerResponseEntity.showFailMsg("密码不能为空");
        }

        InputUserNameEnum inputUserNameEnum = null;

        // 用户名
        if (PrincipalUtil.isUserName(inputUserName)) {
            inputUserNameEnum = InputUserNameEnum.USERNAME;
        }

        if (inputUserNameEnum == null) {
            return ServerResponseEntity.showFailMsg("请输入正确的用户名");
        }

        AuthAccountInVerifyBO authAccountInVerifyBO = authAccountMapper
                .getAuthAccountInVerifyByInputUserName(inputUserNameEnum.value(), inputUserName, sysType);

        if (authAccountInVerifyBO == null) {
            prepareTimingAttackProtection();
            // 再次进行运算，防止计时攻击
            // 计时攻击（Timing
            // attack），通过设备运算的用时来推断出所使用的运算操作，或者通过对比运算的时间推定数据位于哪个存储设备，或者利用通信的时间差进行数据窃取。
            mitigateAgainstTimingAttack(password);
            return ServerResponseEntity.showFailMsg("用户名或密码不正确");
        }

        if (Objects.equals(authAccountInVerifyBO.getStatus(), AuthAccountStatusEnum.DISABLE.value())) {
            return ServerResponseEntity.showFailMsg("用户已禁用，请联系客服");
        }

        if (!passwordEncoder.matches(password, authAccountInVerifyBO.getPassword())) {
            return ServerResponseEntity.showFailMsg("用户名或密码不正确");
        }

        return ServerResponseEntity.success(mapperFacade.map(authAccountInVerifyBO, UserInfoInTokenBO.class));
    }

    @Override
    public AuthAccount getByUserIdAndType(Long userId, Integer sysType) {
        return authAccountMapper.getByUserIdAndType(userId, sysType);
    }

    @Override
    public void updatePassword(Long userId, Integer sysType, String newPassWord) {
        authAccountMapper.updatePassword(userId, sysType, passwordEncoder.encode(newPassWord));
    }

    @Override
    public AuthAccount getByUid(Long uid) {
        return authAccountMapper.getByUid(uid);
    }

    @Override
    public AuthAccount getAccountByInputUserName(String mobile, Integer systemType) {
        return authAccountMapper.getAccountByInputUserName(mobile,systemType);
    }

    /**
     * 防止计时攻击
     */
    private void prepareTimingAttackProtection() {
        if (userNotFoundEncodedPassword == null) {
            userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_SECRET);
        }
    }

    /**
     * 防止计时攻击
     */
    private void mitigateAgainstTimingAttack(String presentedPassword) {
        if (presentedPassword != null) {
            this.passwordEncoder.matches(presentedPassword, userNotFoundEncodedPassword);
        }
    }
}
