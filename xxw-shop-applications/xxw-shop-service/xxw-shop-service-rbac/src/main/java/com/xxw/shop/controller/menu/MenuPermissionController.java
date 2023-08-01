package com.xxw.shop.controller.menu;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.module.menu.dto.MenuPermissionDTO;
import com.xxw.shop.module.menu.dto.MenuPermissionQueryDTO;
import com.xxw.shop.module.menu.entity.MenuPermission;
import com.xxw.shop.module.menu.service.MenuPermissionService;
import com.xxw.shop.module.menu.vo.MenuPermissionVO;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.module.web.response.ServerResponseEntity;
import com.xxw.shop.module.web.security.bo.UserInfoInTokenBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 控制层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@Tag(name = "权限接口")
@RestController
@RequestMapping("/menuPermission")
public class MenuPermissionController {

    @Resource
    private MenuPermissionService menuPermissionService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/list_by_menu")
    @Operation(summary = "获取菜单资源列表", description = "分页获取菜单资源列表")
    public ServerResponseEntity<List<MenuPermissionVO>> listByMenuId(Long menuId) {
        List<MenuPermissionVO> menuPermissionVOList = menuPermissionService.listByMenuId(menuId);
        return ServerResponseEntity.success(menuPermissionVOList);
    }

    @GetMapping
    @Operation(summary = "获取菜单资源", description = "根据menuPermissionId获取菜单资源")
    public ServerResponseEntity<MenuPermissionVO> getByMenuPermissionId(@RequestParam Long menuPermissionId) {
        return ServerResponseEntity.success(menuPermissionService.getByMenuPermissionId(menuPermissionId));
    }

    @PostMapping
    @Operation(summary = "保存菜单资源", description = "保存菜单资源")
    public ServerResponseEntity<Void> save(@Valid @RequestBody MenuPermissionDTO menuPermissionDTO) {
        MenuPermission menuPermission = mapperFacade.map(menuPermissionDTO, MenuPermission.class);
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        menuPermission.setMenuPermissionId(null);
        menuPermission.setBizType(userInfoInTokenBO.getSysType());
        return menuPermissionService.insert(menuPermission);
    }

    @PutMapping
    @Operation(summary = "更新菜单资源", description = "更新菜单资源")
    public ServerResponseEntity<Void> update(@Valid @RequestBody MenuPermissionDTO menuPermissionDTO) {
        MenuPermission menuPermission = mapperFacade.map(menuPermissionDTO, MenuPermission.class);
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        menuPermission.setBizType(userInfoInTokenBO.getSysType());
        return menuPermissionService.update(menuPermission);
    }

    @DeleteMapping
    @Operation(summary = "删除菜单资源", description = "根据菜单资源id删除菜单资源")
    public ServerResponseEntity<Void> delete(@RequestParam Long menuPermissionId) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        menuPermissionService.deleteById(menuPermissionId, userInfoInTokenBO.getSysType());
        return ServerResponseEntity.success();
    }

    @GetMapping(value = "/list")
    @Operation(summary = "获取当前用户拥有的权限", description = "当前用户所拥有的所有权限，精确到按钮，实际上element admin里面的roles就可以理解成权限")
    public ServerResponseEntity<List<String>> permissions() {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        return ServerResponseEntity.success(menuPermissionService.listUserPermissions(userInfoInTokenBO.getUserId(), userInfoInTokenBO.getSysType(), Objects.equals(userInfoInTokenBO.getIsAdmin(), 1)));
    }

    @GetMapping(value = "/page")
    @Operation(summary = "获取当前用户拥有的权限", description = "当前用户所拥有的所有权限，精确到按钮，实际上element admin里面的roles就可以理解成权限")
    public ServerResponseEntity<Page<MenuPermissionVO>> pagePermissions(MenuPermissionQueryDTO dto) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        dto.setSysType(userInfoInTokenBO.getSysType());
        Page<MenuPermissionVO> permissionPage = menuPermissionService.page(dto);
        return ServerResponseEntity.success(permissionPage);
    }

}
