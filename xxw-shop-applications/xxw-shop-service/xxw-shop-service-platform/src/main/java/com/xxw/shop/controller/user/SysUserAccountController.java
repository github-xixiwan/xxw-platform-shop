package com.xxw.shop.controller.user;

import com.xxw.shop.api.auth.vo.AuthAccountVO;
import com.xxw.shop.constant.PlatformBusinessError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.module.user.dto.ChangeAccountDTO;
import com.xxw.shop.module.user.service.SysUserService;
import com.xxw.shop.module.user.vo.SysUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping(value = "/sys_user/account")
@RestController
@Tag(name = "平台用户账号信息")
public class SysUserAccountController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping
    @Operation(summary = "获取账号信息", description = "获取账号信息")
    public ServerResponseEntity<AuthAccountVO> getAccount(Long userId) {
        return sysUserService.getByUserIdAndSysType(userId, AuthUserContext.get().getSysType());
    }


    @PostMapping
    @Operation(summary = "添加账号", description = "添加账号")
    public ServerResponseEntity<Void> addAccount(@Valid @RequestBody ChangeAccountDTO changeAccountDTO) {
        SysUserVO sysUserVO = sysUserService.getByUserId(changeAccountDTO.getUserId());
        if (sysUserVO == null) {
            throw new BusinessException(PlatformBusinessError.PLATFORM_00001);
        }
        if (Objects.equals(sysUserVO.getHasAccount(), 1)) {
            throw new BusinessException(PlatformBusinessError.PLATFORM_00002);
        }
        return sysUserService.save(changeAccountDTO);
    }

    @PutMapping
    @Operation(summary = "修改账号", description = "修改账号")
    public ServerResponseEntity<Void> updateAccount(@Valid @RequestBody ChangeAccountDTO changeAccountDTO) {
        SysUserVO sysUserVO = sysUserService.getByUserId(changeAccountDTO.getUserId());
        if (sysUserVO == null || Objects.equals(sysUserVO.getHasAccount(), 0)) {
            throw new BusinessException(PlatformBusinessError.PLATFORM_00001);
        }
        return sysUserService.updateAccount(changeAccountDTO);
    }
}
