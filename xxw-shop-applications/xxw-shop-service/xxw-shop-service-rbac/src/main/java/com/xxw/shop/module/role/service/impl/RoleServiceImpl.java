package com.xxw.shop.module.role.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.module.role.entity.Role;
import com.xxw.shop.module.role.mapper.RoleMapper;
import com.xxw.shop.module.role.service.RoleService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
