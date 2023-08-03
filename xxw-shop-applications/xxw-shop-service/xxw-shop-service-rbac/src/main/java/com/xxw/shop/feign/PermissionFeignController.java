package com.xxw.shop.feign;

import com.xxw.shop.api.rbac.dto.ClearUserPermissionsCacheDTO;
import com.xxw.shop.api.rbac.feign.PermissionFeignClient;
import com.xxw.shop.module.menu.service.MenuPermissionService;
import com.xxw.shop.module.menu.vo.UriPermissionVO;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class PermissionFeignController implements PermissionFeignClient {

    @Resource
    private MenuPermissionService menuPermissionService;

    @Override
    public ServerResponseEntity<Boolean> checkPermission(@RequestParam("userId") Long userId, @RequestParam("sysType") Integer sysType, @RequestParam("uri") String uri, @RequestParam("isAdmin") Integer isAdmin, @RequestParam("method") Integer method) {
        // 目前该用户拥有的权限
        List<String> userPermissions = menuPermissionService.listUserPermissions(userId, sysType, Objects.equals(isAdmin, 1));

        // uri,方法对应的权限 map
        List<UriPermissionVO> uriPermissions = menuPermissionService.listUriPermissionInfo(sysType);

        AntPathMatcher pathMatcher = new AntPathMatcher();

        // 看看该uri对应需要什么权限
        for (UriPermissionVO uriPermission : uriPermissions) {
            // uri + 请求方式匹配
            if (pathMatcher.match(uriPermission.getUri(), uri) && Objects.equals(uriPermission.getMethod(), method)) {
                // uri 用户有这个权限
                if (userPermissions.contains(uriPermission.getPermission())) {
                    return ServerResponseEntity.success(Boolean.TRUE);
                } else {
                    return ServerResponseEntity.fail(SystemErrorEnumError.UNAUTHORIZED);
                }
            }
        }

        // 如果uri 没有匹配到，则说明uri不需要权限，直接校验成功
        return ServerResponseEntity.success(Boolean.TRUE);
    }

    @Override
    public ServerResponseEntity<Void> clearUserPermissionsCache(ClearUserPermissionsCacheDTO clearUserPermissionsCacheDTO) {
        menuPermissionService.clearUserPermissionsCache(clearUserPermissionsCacheDTO.getUserId(), clearUserPermissionsCacheDTO.getSysType());
        return ServerResponseEntity.success();
    }

}
