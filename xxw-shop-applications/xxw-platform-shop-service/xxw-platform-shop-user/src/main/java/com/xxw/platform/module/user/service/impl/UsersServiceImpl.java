package com.xxw.shop.module.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.entity.Users;
import com.xxw.shop.mapper.UsersMapper;
import com.xxw.shop.service.UsersService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author liaoxiting
 * @since 2023-07-29
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
