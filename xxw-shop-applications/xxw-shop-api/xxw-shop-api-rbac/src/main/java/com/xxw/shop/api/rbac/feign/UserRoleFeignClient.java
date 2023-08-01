package com.xxw.shop.api.rbac.feign;

import com.xxw.shop.api.rbac.dto.UserRoleDTO;
import com.xxw.shop.module.web.response.ServerResponseEntity;
import com.xxw.shop.module.web.security.FeignInsideAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "shop-rbac",contextId = "userRole")
public interface UserRoleFeignClient {


	/**
	 * 保存用户角色关联信息
	 * @param userRoleDTO 用户角色关联信息
	 * @return void
	 */
	@PostMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/userRole/saveByUserIdAndSysType")
	ServerResponseEntity<Void> saveByUserIdAndSysType(@RequestBody UserRoleDTO userRoleDTO);

	/**
	 * 更新用户角色关联信息
	 * @param userRoleDTO 用户角色关联信息
	 * @return void
	 */
	@PutMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/userRole/updateByUserIdAndSysType")
	ServerResponseEntity<Void> updateByUserIdAndSysType(@RequestBody UserRoleDTO userRoleDTO);

	/**
	 * 删除用户角色关联信息
	 * @param userId 用户id
	 * @return void
	 */
	@DeleteMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/userRole/deleteByUserIdAndSysType")
	ServerResponseEntity<Void> deleteByUserIdAndSysType(@RequestParam("userId") Long userId);

	/**
	 * 获取用户角色关联信息
	 * @param userId 用户id
	 * @return 用户角色关联ids
	 */
	@GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/userRole/getRoleIds")
	ServerResponseEntity<List<Long>> getRoleIds(@RequestParam("userId") Long userId);
}
