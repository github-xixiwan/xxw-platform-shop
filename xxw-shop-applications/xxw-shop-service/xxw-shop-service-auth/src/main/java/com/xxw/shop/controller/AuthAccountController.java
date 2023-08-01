package com.xxw.shop.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.xxw.shop.module.entity.AuthAccount;
import com.xxw.shop.module.service.AuthAccountService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 *  控制层。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
@RestController
@RequestMapping("/authAccount")
public class AuthAccountController {

    @Autowired
    private AuthAccountService authAccountService;

    /**
     * 添加。
     *
     * @param authAccount 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody AuthAccount authAccount) {
        return authAccountService.save(authAccount);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return authAccountService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param authAccount 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody AuthAccount authAccount) {
        return authAccountService.updateById(authAccount);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<AuthAccount> list() {
        return authAccountService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public AuthAccount getInfo(@PathVariable Serializable id) {
        return authAccountService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<AuthAccount> page(Page<AuthAccount> page) {
        return authAccountService.page(page);
    }

}
