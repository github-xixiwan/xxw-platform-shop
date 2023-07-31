package com.xxw.shop.controller.menu;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.xxw.shop.module.menu.entity.MenuPermission;
import com.xxw.shop.module.menu.service.MenuPermissionService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 *  控制层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@RestController
@RequestMapping("/menuPermission")
public class MenuPermissionController {

    @Autowired
    private MenuPermissionService menuPermissionService;

    /**
     * 添加。
     *
     * @param menuPermission 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody MenuPermission menuPermission) {
        return menuPermissionService.save(menuPermission);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return menuPermissionService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param menuPermission 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MenuPermission menuPermission) {
        return menuPermissionService.updateById(menuPermission);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MenuPermission> list() {
        return menuPermissionService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public MenuPermission getInfo(@PathVariable Serializable id) {
        return menuPermissionService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<MenuPermission> page(Page<MenuPermission> page) {
        return menuPermissionService.page(page);
    }

}
