package com.xxw.shop.controller.menu;

import com.xxw.shop.constant.RbacBusinessError;
import com.xxw.shop.module.menu.dto.MenuDTO;
import com.xxw.shop.module.menu.entity.Menu;
import com.xxw.shop.module.menu.service.MenuService;
import com.xxw.shop.module.menu.vo.MenuSimpleVO;
import com.xxw.shop.module.menu.vo.MenuVO;
import com.xxw.shop.module.menu.vo.RouteMetaVO;
import com.xxw.shop.module.menu.vo.RouteVO;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.module.util.exception.BusinessException;
import com.xxw.shop.module.web.response.ServerResponseEntity;
import com.xxw.shop.module.web.security.bo.UserInfoInTokenBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 控制层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@Tag(name = "菜单接口")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping(value = "/route")
    @Operation(summary = "路由菜单", description = "获取当前登陆用户可用的路由菜单列表")
    public ServerResponseEntity<List<RouteVO>> route(Integer sysType) {
        sysType = Objects.isNull(sysType) ? AuthUserContext.get().getSysType() : sysType;
        List<Menu> menus = menuService.listBySysType(sysType);

        List<RouteVO> routes = new ArrayList<>(menus.size());

        for (Menu menu : menus) {
            RouteVO route = new RouteVO();
            route.setAlwaysShow(Objects.equals(menu.getAlwaysShow(), 1));
            route.setComponent(menu.getComponent());
            route.setHidden(Objects.equals(menu.getHidden(), 1));
            route.setName(menu.getName());
            route.setPath(menu.getPath());
            route.setRedirect(menu.getRedirect());
            route.setId(menu.getMenuId());
            route.setParentId(menu.getParentId());
            route.setSeq(menu.getSeq());

            RouteMetaVO meta = new RouteMetaVO();
            meta.setActiveMenu(menu.getActiveMenu());
            meta.setAffix(Objects.equals(menu.getAffix(), 1));
            meta.setBreadcrumb(Objects.equals(menu.getBreadcrumb(), 1));
            meta.setIcon(menu.getIcon());
            meta.setNoCache(Objects.equals(menu.getNoCache(), 1));
            meta.setTitle(menu.getTitle());
            // 对于前端来说角色就是权限
            meta.setRoles(Collections.singletonList(menu.getPermission()));

            route.setMeta(meta);
            routes.add(route);
        }
        return ServerResponseEntity.success(routes);
    }

    @GetMapping
    @Operation(summary = "获取菜单管理", description = "根据menuId获取菜单管理")
    public ServerResponseEntity<MenuVO> getByMenuId(@RequestParam Long menuId) {
        return ServerResponseEntity.success(menuService.getByMenuId(menuId));
    }

    @PostMapping
    @Operation(summary = "保存菜单管理", description = "保存菜单管理")
    public ServerResponseEntity<Void> save(@Valid @RequestBody MenuDTO menuDTO) {
        Menu menu = checkAndGenerate(menuDTO);
        menu.setMenuId(null);
        menuService.save(menu);
        return ServerResponseEntity.success();
    }

    private Menu checkAndGenerate(@RequestBody @Valid MenuDTO menuDTO) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        if (!Objects.equals(userInfoInTokenBO.getTenantId(), 0L)) {
            throw new BusinessException(RbacBusinessError.RBAC_00002);
        }
        Menu menu = mapperFacade.map(menuDTO, Menu.class);
        menu.setBizType(menuDTO.getSysType());
        if (Objects.isNull(menuDTO.getSysType())) {
            menu.setBizType(AuthUserContext.get().getSysType());
        }
        return menu;
    }

    @PutMapping
    @Operation(summary = "更新菜单管理", description = "更新菜单管理")
    public ServerResponseEntity<Void> update(@Valid @RequestBody MenuDTO menuDTO) {
        Menu menu = checkAndGenerate(menuDTO);
        menuService.modify(menu);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除菜单管理", description = "根据菜单管理id删除菜单管理")
    public ServerResponseEntity<Void> delete(@RequestParam("menuId") Long menuId, @RequestParam("sysType") Integer sysType) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        if (!Objects.equals(userInfoInTokenBO.getTenantId(), 0L)) {
            throw new BusinessException(RbacBusinessError.RBAC_00002);
        }
        sysType = Objects.isNull(sysType) ? userInfoInTokenBO.getSysType() : sysType;
        menuService.removeById(menuId, sysType);
        return ServerResponseEntity.success();
    }

    @GetMapping(value = "/list_with_permissions")
    @Operation(summary = "菜单列表和按钮列表", description = "根据系统类型获取该系统的菜单列表 + 菜单下的权限列表")
    public ServerResponseEntity<List<MenuSimpleVO>> listWithPermissions() {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        List<MenuSimpleVO> menuList = menuService.listWithPermissions(userInfoInTokenBO.getSysType());
        return ServerResponseEntity.success(menuList);
    }

    @GetMapping(value = "/list_menu_ids")
    @Operation(summary = "获取当前用户可见的菜单ids", description = "获取当前用户可见的菜单id")
    public ServerResponseEntity<List<Long>> listMenuIds() {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        List<Long> menuList = menuService.listMenuIds(userInfoInTokenBO.getUserId());
        return ServerResponseEntity.success(menuList);
    }

}
