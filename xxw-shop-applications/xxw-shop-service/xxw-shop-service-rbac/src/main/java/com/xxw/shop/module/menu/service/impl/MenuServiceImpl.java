package com.xxw.shop.module.menu.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.module.menu.entity.Menu;
import com.xxw.shop.module.menu.mapper.MenuMapper;
import com.xxw.shop.module.menu.service.MenuService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
