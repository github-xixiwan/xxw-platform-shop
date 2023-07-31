package com.xxw.shop.module.menu.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public class MenuTableDef extends TableDef {

    /**
     * 
     */
    public static final MenuTableDef MENU = new MenuTableDef();

    /**
     * 排序，越小越靠前
     */
    public final QueryColumn SEQ = new QueryColumn(this, "seq");

    /**
     * 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon
     */
    public final QueryColumn ICON = new QueryColumn(this, "icon");

    /**
     * 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 路径 就像uri
     */
    public final QueryColumn PATH = new QueryColumn(this, "path");

    /**
     * 若果设置为true，它则会固定在tags-view中(默认 false)
     */
    public final QueryColumn AFFIX = new QueryColumn(this, "affix");

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    public final QueryColumn TITLE = new QueryColumn(this, "title");

    /**
     * 当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
     */
    public final QueryColumn HIDDEN = new QueryColumn(this, "hidden");

    /**
     * 菜单id
     */
    public final QueryColumn MENU_ID = new QueryColumn(this, "menu_id");

    /**
     * 业务类型 1 店铺菜单 2平台菜单
     */
    public final QueryColumn BIZ_TYPE = new QueryColumn(this, "biz_type");

    /**
     * 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
     */
    public final QueryColumn NO_CACHE = new QueryColumn(this, "no_cache");

    /**
     * 父菜单ID，一级菜单为0
     */
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");

    /**
     * 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    public final QueryColumn REDIRECT = new QueryColumn(this, "redirect");

    /**
     * 1.'Layout' 为布局，不会跳页面 2.'components-demo/tinymce' 跳转到该页面
     */
    public final QueryColumn COMPONENT = new QueryColumn(this, "component");

    /**
     * 当路由设置了该属性，则会高亮相对应的侧边栏。
     */
    public final QueryColumn ACTIVE_MENU = new QueryColumn(this, "active_menu");

    /**
     * 一直显示根路由
     */
    public final QueryColumn ALWAYS_SHOW = new QueryColumn(this, "always_show");

    /**
     * 如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)
     */
    public final QueryColumn BREADCRUMB = new QueryColumn(this, "breadcrumb");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 权限，需要有哪个权限才能访问该菜单
     */
    public final QueryColumn PERMISSION = new QueryColumn(this, "permission");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{MENU_ID, CREATE_TIME, UPDATE_TIME, PARENT_ID, BIZ_TYPE, PERMISSION, PATH, COMPONENT, REDIRECT, ALWAYS_SHOW, HIDDEN, NAME, TITLE, ICON, NO_CACHE, BREADCRUMB, AFFIX, ACTIVE_MENU, SEQ};

    public MenuTableDef() {
        super("", "menu");
    }

}
