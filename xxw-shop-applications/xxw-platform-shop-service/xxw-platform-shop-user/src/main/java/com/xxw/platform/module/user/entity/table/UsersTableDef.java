package com.xxw.shop.module.user.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-07-29
 */
public class UsersTableDef extends TableDef {

    /**
     * 
     */
    public static final UsersTableDef USERS = new UsersTableDef();

    /**
     * 主键id 用户id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    public final QueryColumn SEX = new QueryColumn(this, "sex");

    /**
     * 头像
     */
    public final QueryColumn FACE = new QueryColumn(this, "face");

    /**
     * 邮箱地址 邮箱地址
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * 手机号 手机号
     */
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");

    /**
     * 生日 生日
     */
    public final QueryColumn BIRTHDAY = new QueryColumn(this, "birthday");

    /**
     * 昵称 昵称
     */
    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");

    /**
     * 密码 密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 真实姓名
     */
    public final QueryColumn REALNAME = new QueryColumn(this, "realname");

    /**
     * 用户名 用户名
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    /**
     * 创建时间 创建时间
     */
    public final QueryColumn CREATED_TIME = new QueryColumn(this, "created_time");

    /**
     * 更新时间 更新时间
     */
    public final QueryColumn UPDATED_TIME = new QueryColumn(this, "updated_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USERNAME, PASSWORD, NICKNAME, REALNAME, FACE, MOBILE, EMAIL, SEX, BIRTHDAY, CREATED_TIME, UPDATED_TIME};

    public UsersTableDef() {
        super("", "users");
    }

}
