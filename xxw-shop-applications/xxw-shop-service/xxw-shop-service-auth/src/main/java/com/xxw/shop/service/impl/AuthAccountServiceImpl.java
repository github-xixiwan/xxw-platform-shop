package com.xxw.shop.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.module.entity.AuthAccount;
import com.xxw.shop.module.mapper.AuthAccountMapper;
import com.xxw.shop.module.service.AuthAccountService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
@Service
public class AuthAccountServiceImpl extends ServiceImpl<AuthAccountMapper, AuthAccount> implements AuthAccountService {

}
