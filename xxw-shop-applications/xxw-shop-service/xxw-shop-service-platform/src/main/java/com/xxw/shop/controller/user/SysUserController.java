package com.xxw.shop.controller.user;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.module.user.dto.SysUserDTO;
import com.xxw.shop.module.user.dto.SysUserQueryDTO;
import com.xxw.shop.module.user.entity.SysUser;
import com.xxw.shop.module.user.service.SysUserService;
import com.xxw.shop.module.user.vo.SysUserSimpleVO;
import com.xxw.shop.module.user.vo.SysUserVO;
import com.xxw.shop.module.web.response.ServerResponseEntity;
import com.xxw.shop.module.web.security.bo.UserInfoInTokenBO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 控制层。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
@RestController
@RequestMapping("/sys_user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/info")
    @Operation(summary = "登陆平台用户信息", description = "获取当前登陆平台用户的用户信息")
    public ServerResponseEntity<SysUserSimpleVO> info() {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        SysUserSimpleVO sysUserSimple = sysUserService.getSimpleByUserId(userInfoInTokenBO.getUserId());
        sysUserSimple.setIsAdmin(userInfoInTokenBO.getIsAdmin());
        return ServerResponseEntity.success(sysUserSimple);
    }

    @GetMapping("/page")
    @Operation(summary = "平台用户列表", description = "获取平台用户列表")
    public ServerResponseEntity<Page<SysUserVO>> page(@Valid SysUserQueryDTO dto) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        dto.setShopId(userInfoInTokenBO.getTenantId());
        Page<SysUserVO> sysUserPage = sysUserService.pageByShopId(dto);
        return ServerResponseEntity.success(sysUserPage);
    }

    @GetMapping
    @Operation(summary = "获取平台用户信息", description = "根据用户id获取平台用户信息")
    public ServerResponseEntity<SysUserVO> detail(@RequestParam Long sysUserId) {
        return ServerResponseEntity.success(sysUserService.getByUserId(sysUserId));
    }

    @PostMapping
    @Operation(summary = "保存平台用户信息", description = "保存平台用户信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody SysUserDTO sysUserDTO) {
        SysUser sysUser = mapperFacade.map(sysUserDTO, SysUser.class);
        sysUser.setSysUserId(null);
        sysUser.setHasAccount(0);
        sysUserService.save(sysUser, sysUserDTO.getRoleIds());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新平台用户信息", description = "更新平台用户信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody SysUserDTO sysUserDTO) {
        SysUser sysUser = mapperFacade.map(sysUserDTO, SysUser.class);
        sysUserService.update(sysUser, sysUserDTO.getRoleIds());
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除平台用户信息", description = "根据平台用户id删除平台用户信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long sysUserId) {
        sysUserService.deleteById(sysUserId);
        return ServerResponseEntity.success();
    }

}
