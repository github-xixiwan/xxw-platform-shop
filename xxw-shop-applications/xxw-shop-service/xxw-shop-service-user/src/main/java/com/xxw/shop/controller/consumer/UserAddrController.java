package com.xxw.shop.controller.consumer;

import com.xxw.shop.api.user.vo.UserAddrVO;
import com.xxw.shop.constant.UserBusinessError;
import com.xxw.shop.dto.UserAddrDTO;
import com.xxw.shop.entity.UserAddr;
import com.xxw.shop.module.common.constant.Constant;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.UserAddrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("consumerUserAddrController")
@RequestMapping("/user_addr")
@Tag(name = "consumer-用户地址")
public class UserAddrController {

    @Resource
    private UserAddrService userAddrService;

    @Resource
    private MapperFacade mapperFacade;

    private static final Integer MAX_USER_ADDR = 10;

    @GetMapping("/list")
    @Operation(summary = "获取用户地址列表", description = "获取用户地址列表")
    public ServerResponseEntity<List<UserAddrVO>> list() {
        Long userId = AuthUserContext.get().getUserId();
        List<UserAddrVO> userAddrPage = userAddrService.lists(userId);
        return ServerResponseEntity.success(userAddrPage);
    }

    @GetMapping
    @Operation(summary = "获取用户地址", description = "根据addrId获取用户地址")
    public ServerResponseEntity<UserAddrVO> getByAddrId(@RequestParam Long addrId) {
        return ServerResponseEntity.success(userAddrService.getUserAddrByUserId(addrId,
                AuthUserContext.get().getUserId()));
    }

    @PostMapping
    @Operation(summary = "保存用户地址", description = "保存用户地址")
    public ServerResponseEntity<Void> save(@Valid @RequestBody UserAddrDTO userAddrDTO) {
        Long userId = AuthUserContext.get().getUserId();
        long userAddrCount = userAddrService.countByUserId(userId);
        if (userAddrCount >= MAX_USER_ADDR) {
            throw new BusinessException(UserBusinessError.USER_00003);
        }
        UserAddr userAddr = mapperFacade.map(userAddrDTO, UserAddr.class);
        if (userAddrCount == 0) {
            userAddr.setIsDefault(Constant.DEFAULT_ADDR);
        } else if (!Constant.DEFAULT_ADDR.equals(userAddr.getIsDefault())) {
            userAddr.setIsDefault(Constant.NOT_DEFAULT_ADDR);
        }
        userAddr.setAddrId(null);
        userAddr.setUserId(userId);
        userAddrService.save(userAddr);
        // 清除默认地址缓存
        if (Constant.DEFAULT_ADDR.equals(userAddr.getIsDefault())) {
            userAddrService.removeUserDefaultAddrCacheByUserId(userId);
        }
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新用户地址", description = "更新用户地址")
    public ServerResponseEntity<Void> update(@Valid @RequestBody UserAddrDTO userAddrDTO) {
        Long userId = AuthUserContext.get().getUserId();
        UserAddrVO dbUserAddr = userAddrService.getUserAddrByUserId(userAddrDTO.getAddrId(), userId);
        if (dbUserAddr == null) {
            throw new BusinessException(UserBusinessError.USER_00004);
        }
        // 默认地址不能修改为普通地址
        else if (dbUserAddr.getIsDefault().equals(Constant.DEFAULT_ADDR) && userAddrDTO.getIsDefault().equals(Constant.NOT_DEFAULT_ADDR)) {
            throw new BusinessException(SystemErrorEnumError.DATA_ERROR);
        }
        UserAddr userAddr = mapperFacade.map(userAddrDTO, UserAddr.class);
        userAddr.setUserId(userId);
        userAddrService.updateUserAddr(userAddr);
        // 清除默认地址缓存
        if (userAddr.getIsDefault().equals(Constant.DEFAULT_ADDR)) {
            userAddrService.removeUserDefaultAddrCacheByUserId(userId);
        }
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除用户地址", description = "根据用户地址id删除用户地址")
    public ServerResponseEntity<Void> delete(@RequestParam Long addrId) {
        Long userId = AuthUserContext.get().getUserId();
        UserAddrVO dbUserAddr = userAddrService.getUserAddrByUserId(addrId, userId);
        if (dbUserAddr == null) {
            throw new BusinessException(UserBusinessError.USER_00004);
        } else if (dbUserAddr.getIsDefault().equals(Constant.DEFAULT_ADDR)) {
            throw new BusinessException(UserBusinessError.USER_00005);
        }
        userAddrService.deleteUserAddrByUserId(addrId, userId);
        return ServerResponseEntity.success();
    }

}
