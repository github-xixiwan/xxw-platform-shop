package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-11
 */
public class UserTableDef extends TableDef {

    /**
     * 
     */
    public static final UserTableDef USER = new UserTableDef();

    /**
     * 头像图片路径
     */
    public final QueryColumn PIC = new QueryColumn(this, "pic");

    /**
     * 状态 1 正常 0 无效
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * ID
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 用户昵称
     */
    public final QueryColumn NICK_NAME = new QueryColumn(this, "nick_name");

    /**
     * 注册时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 修改时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{USER_ID, CREATE_TIME, UPDATE_TIME, NICK_NAME, PIC, STATUS};

    public UserTableDef() {
        super("", "user");
    }

}
