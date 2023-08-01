package com.xxw.shop.controller.role;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.module.role.dto.RoleDTO;
import com.xxw.shop.module.role.dto.RoleQueryDTO;
import com.xxw.shop.module.role.entity.Role;
import com.xxw.shop.module.role.service.RoleService;
import com.xxw.shop.module.role.vo.RoleVO;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.module.web.constant.SystemErrorEnumError;
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
@Tag(name = "角色")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/page")
    @Operation(summary = "分页获取角色列表", description = "分页获取角色列表")
    public ServerResponseEntity<Page<RoleVO>> page(@Valid RoleQueryDTO dto) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        dto.setSysType(userInfoInTokenBO.getSysType());
        dto.setTenantId(userInfoInTokenBO.getTenantId());
        Page<RoleVO> rolePage = roleService.page(dto);
        return ServerResponseEntity.success(rolePage);
    }

    @GetMapping("/list")
    @Operation(summary = "获取角色列表", description = "分页获取角色列表")
    public ServerResponseEntity<List<RoleVO>> list() {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        return ServerResponseEntity.success(roleService.list(userInfoInTokenBO.getSysType(), userInfoInTokenBO.getTenantId()));
    }

    @GetMapping
    @Operation(summary = "获取角色", description = "根据roleId获取角色")
    public ServerResponseEntity<RoleVO> getByRoleId(@RequestParam Long roleId) {
        return ServerResponseEntity.success(roleService.getByRoleId(roleId));
    }

    @PostMapping
    @Operation(summary = "保存角色", description = "保存角色")
    public ServerResponseEntity<Void> save(@Valid @RequestBody RoleDTO roleDTO) {
        Role role = mapperFacade.map(roleDTO, Role.class);
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        role.setBizType(userInfoInTokenBO.getSysType());
        role.setRoleId(null);
        role.setCreateUserId(userInfoInTokenBO.getUserId());
        role.setTenantId(userInfoInTokenBO.getTenantId());
        roleService.save(role, roleDTO.getMenuIds(), roleDTO.getMenuPermissionIds());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新角色", description = "更新角色")
    public ServerResponseEntity<Void> update(@Valid @RequestBody RoleDTO roleDTO) {

        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();


        RoleVO dbRole = roleService.getByRoleId(roleDTO.getRoleId());

        if (!Objects.equals(dbRole.getBizType(), userInfoInTokenBO.getSysType()) || !Objects.equals(dbRole.getTenantId(), userInfoInTokenBO.getTenantId())) {
            return ServerResponseEntity.fail(SystemErrorEnumError.UNAUTHORIZED);
        }
        Role role = mapperFacade.map(roleDTO, Role.class);
        role.setBizType(userInfoInTokenBO.getSysType());

        roleService.update(role, roleDTO.getMenuIds(), roleDTO.getMenuPermissionIds());
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除角色", description = "根据角色id删除角色")
    public ServerResponseEntity<Void> delete(@RequestParam Long roleId) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();

        RoleVO dbRole = roleService.getByRoleId(roleId);

        if (!Objects.equals(dbRole.getBizType(), userInfoInTokenBO.getSysType()) || !Objects.equals(dbRole.getTenantId(), userInfoInTokenBO.getTenantId())) {
            return ServerResponseEntity.fail(SystemErrorEnumError.UNAUTHORIZED);
        }
        roleService.deleteById(roleId, userInfoInTokenBO.getSysType());
        return ServerResponseEntity.success();
    }

}
