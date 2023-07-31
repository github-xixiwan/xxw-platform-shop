package com.xxw.shop.module.menu.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public class MenuPermissionTableDef extends TableDef {

    /**
     * 
     */
    public static final MenuPermissionTableDef MENU_PERMISSION = new MenuPermissionTableDef();

    /**
     * 资源对应服务器路径
     */
    public final QueryColumn URI = new QueryColumn(this, "uri");

    /**
     * 资源名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 资源关联菜单
     */
    public final QueryColumn MENU_ID = new QueryColumn(this, "menu_id");

    /**
     * 请求方法 1.GET 2.POST 3.PUT 4.DELETE
     */
    public final QueryColumn METHOD = new QueryColumn(this, "method");

    /**
     * 业务类型 1 店铺菜单 2平台菜单
     */
    public final QueryColumn BIZ_TYPE = new QueryColumn(this, "biz_type");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 权限对应的编码
     */
    public final QueryColumn PERMISSION = new QueryColumn(this, "permission");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 菜单资源用户id
     */
    public final QueryColumn MENU_PERMISSION_ID = new QueryColumn(this, "menu_permission_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{MENU_PERMISSION_ID, CREATE_TIME, UPDATE_TIME, MENU_ID, BIZ_TYPE, PERMISSION, NAME, URI, METHOD};

    public MenuPermissionTableDef() {
        super("", "menu_permission");
    }

}
