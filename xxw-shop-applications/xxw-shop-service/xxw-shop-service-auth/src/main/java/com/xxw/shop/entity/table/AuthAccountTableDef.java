package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
public class AuthAccountTableDef extends TableDef {

    /**
     * 
     */
    public static final AuthAccountTableDef AUTH_ACCOUNT = new AuthAccountTableDef();

    /**
     * 全平台用户唯一id
     */
    public final QueryColumn UID = new QueryColumn(this, "uid");

    /**
     * 状态 1:启用 0:禁用 -1:删除
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 是否是管理员
     */
    public final QueryColumn IS_ADMIN = new QueryColumn(this, "is_admin");

    /**
     * 用户类型见SysTypeEnum 0.普通用户系统 1.商家端 2平台端
     */
    public final QueryColumn SYS_TYPE = new QueryColumn(this, "sys_type");

    /**
     * 创建ip
     */
    public final QueryColumn CREATE_IP = new QueryColumn(this, "create_ip");

    /**
     * 密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 所属租户
     */
    public final QueryColumn TENANT_ID = new QueryColumn(this, "tenant_id");

    /**
     * 用户名
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{UID, CREATE_TIME, UPDATE_TIME, USERNAME, PASSWORD, CREATE_IP, STATUS, SYS_TYPE, USER_ID, TENANT_ID, IS_ADMIN};

    public AuthAccountTableDef() {
        super("", "auth_account");
    }

}
