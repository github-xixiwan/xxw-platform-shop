package com.xxw.shop.module.user.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
public class UserRoleTableDef extends TableDef {

    /**
     * 
     */
    public static final UserRoleTableDef USER_ROLE = new UserRoleTableDef();

    /**
     * 关联id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 角色ID
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 用户ID
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CREATE_TIME, UPDATE_TIME, USER_ID, ROLE_ID};

    public UserRoleTableDef() {
        super("", "user_role");
    }

}
