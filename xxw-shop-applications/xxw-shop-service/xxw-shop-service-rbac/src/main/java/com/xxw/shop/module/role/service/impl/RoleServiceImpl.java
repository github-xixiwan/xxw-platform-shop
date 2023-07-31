package com.xxw.shop.module.role.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.module.role.dto.RoleQueryDTO;
import com.xxw.shop.module.role.entity.Role;
import com.xxw.shop.module.role.entity.RoleMenu;
import com.xxw.shop.module.role.mapper.RoleMapper;
import com.xxw.shop.module.role.mapper.RoleMenuMapper;
import com.xxw.shop.module.role.service.RoleService;
import com.xxw.shop.module.role.vo.RoleVO;
import com.xxw.shop.module.user.mapper.UserRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public Page<RoleVO> page(RoleQueryDTO dto) {
        return mapper.list(new Page<>(dto.getPageNumber(), dto.getPageSize()), dto);
    }

    @Override
    public List<RoleVO> list(Integer sysType, Long tenantId) {
        RoleQueryDTO dto = new RoleQueryDTO();
        dto.setSysType(sysType);
        dto.setTenantId(tenantId);
        return mapper.list(dto);
    }

    @Override
    public RoleVO getByRoleId(Long roleId) {
        RoleVO role = mapper.getByRoleId(roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.getByRoleId(roleId);
        role.setMenuIds(roleMenus.stream().filter(roleMenu -> roleMenu.getMenuId() != null).map(RoleMenu::getMenuId).collect(Collectors.toList()));
        role.setMenuPermissionIds(roleMenus.stream().filter(roleMenu -> roleMenu.getMenuPermissionId() != null).map(RoleMenu::getMenuPermissionId).collect(Collectors.toList()));
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Role role, List<Long> menuIds, List<Long> menuPermissionIds) {
        mapper.save(role);
        insertMenuAndPermission(role.getRoleId(), menuIds, menuPermissionIds);
    }


    @Override
    public void update(Role role, List<Long> menuIds, List<Long> menuPermissionIds) {
        mapper.update(role);
        roleMenuMapper.deleteByRoleId(role.getRoleId());
        insertMenuAndPermission(role.getRoleId(), menuIds, menuPermissionIds);
    }

    private void insertMenuAndPermission(Long roleId, List<Long> menuIds, List<Long> menuPermissionIds) {
        if (CollectionUtil.isNotEmpty(menuIds)) {
            List<RoleMenu> roleMenus = menuIds.stream().map(menuId -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuMapper.insertBatch(roleMenus);
        }

        if (CollectionUtil.isNotEmpty(menuPermissionIds)) {
            List<RoleMenu> roleMenus = menuPermissionIds.stream().map(menuPermissionId -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuPermissionId(menuPermissionId);
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuMapper.insertBatch(roleMenus);
        }
    }

    @Override
    public void deleteById(Long roleId, Integer sysType) {
        mapper.deleteById(roleId, sysType);
        roleMenuMapper.deleteByRoleId(roleId);
        userRoleMapper.deleteByRoleId(roleId);
    }

    @Override
    public Integer getBizType(Long roleId) {
        return mapper.getBizType(roleId);
    }
}
