package com.xxw.shop.controller.business;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.api.user.vo.UserCompleteVO;
import com.xxw.shop.dto.UserDTO;
import com.xxw.shop.dto.UserQueryDTO;
import com.xxw.shop.entity.User;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表
 */
@RestController("businessUserController")
@RequestMapping("/m/user")
@Tag(name = "店铺-用户表")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/page")
    @Operation(summary = "获取用户表列表", description = "分页获取用户表列表")
    public ServerResponseEntity<Page<UserCompleteVO>> page(@Valid UserQueryDTO dto) {
        return ServerResponseEntity.success(userService.page(dto));
    }

    @GetMapping
    @Operation(summary = "获取用户表", description = "根据userId获取用户表")
    public ServerResponseEntity<UserCompleteVO> getByUserId(@RequestParam Long userId) {
        UserCompleteVO userVO = mapperFacade.map(userService.getByUserId(userId), UserCompleteVO.class);
        return ServerResponseEntity.success(userVO);
    }

    @PutMapping
    @Operation(summary = "更新用户表", description = "更新用户表")
    public ServerResponseEntity<Void> update(@Valid @RequestBody UserDTO userDTO) {
        User user = mapperFacade.map(userDTO, User.class);
        userService.updateUser(user);
        return ServerResponseEntity.success();
    }

}
