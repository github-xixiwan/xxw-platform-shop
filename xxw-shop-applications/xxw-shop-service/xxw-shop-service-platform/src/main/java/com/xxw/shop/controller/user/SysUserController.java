package com.xxw.shop.controller.user;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.module.user.entity.SysUser;
import com.xxw.shop.module.user.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 *  控制层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Value("${name:word}")
    private String name;

    @Resource
    private SysUserService sysUserService;

    /**
     * 添加。
     *
     * @param sysUser 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody SysUser sysUser) {
        return sysUserService.save(sysUser);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return sysUserService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param sysUser 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody SysUser sysUser) {
        return sysUserService.updateById(sysUser);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<SysUser> list() {
        return sysUserService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public SysUser getInfo(@PathVariable Serializable id) {
        return sysUserService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<SysUser> page(Page<SysUser> page) {
        return sysUserService.page(page);
    }

}
