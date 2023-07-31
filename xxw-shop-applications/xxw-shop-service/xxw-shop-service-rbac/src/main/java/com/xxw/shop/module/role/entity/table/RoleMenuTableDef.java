package com.xxw.shop.module.role.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public class RoleMenuTableDef extends TableDef {

    /**
     * 
     */
    public static final RoleMenuTableDef ROLE_MENU = new RoleMenuTableDef();

    /**
     * 关联id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 菜单ID
     */
    public final QueryColumn MENU_ID = new QueryColumn(this, "menu_id");

    /**
     * 角色ID
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CREATE_TIME, UPDATE_TIME, ROLE_ID, MENU_ID, MENU_PERMISSION_ID};

    public RoleMenuTableDef() {
        super("", "role_menu");
    }

}
