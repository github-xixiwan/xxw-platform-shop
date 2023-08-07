package com.xxw.shop.module.menu.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.module.menu.entity.Menu;
import com.xxw.shop.module.menu.vo.MenuSimpleVO;
import com.xxw.shop.module.menu.vo.MenuVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据菜单管理id获取菜单管理
     *
     * @param menuId 菜单管理id
     * @return 菜单管理
     */
    MenuVO getByMenuId(Long menuId);

    /**
     * 保存菜单管理
     *
     * @param menu 菜单管理
     * @return
     */
    boolean saveMenu(Menu menu);

    /**
     * 更新菜单管理
     *
     * @param menu 菜单管理
     */
    void modify(Menu menu);

    /**
     * 根据菜单管理id删除菜单管理
     *
     * @param menuId  菜单id
     * @param sysType 系统类型
     */
    void removeById(Long menuId, Integer sysType);

    /**
     * 根据系统类型获取该系统的菜单列表
     *
     * @param sysType 系统类型
     * @return 菜单列表
     */
    List<Menu> listBySysType(Integer sysType);

    /**
     * 根据系统类型获取该系统的菜单列表 + 菜单下的权限列表
     *
     * @param sysType 系统类型
     * @return 菜单列表 + 菜单下的权限列表
     */
    List<MenuSimpleVO> listWithPermissions(Integer sysType);

    /**
     * 获取当前用户可见的菜单ids
     *
     * @param userId 用户id
     * @return 菜单列表
     */
    List<Long> listMenuIds(Long userId);

}
