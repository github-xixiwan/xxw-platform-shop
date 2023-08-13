package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public class ShopUserTableDef extends TableDef {

    /**
     * 
     */
    public static final ShopUserTableDef SHOP_USER = new ShopUserTableDef();

    /**
     * 员工编号
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 头像
     */
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    /**
     * 关联店铺id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 昵称
     */
    public final QueryColumn NICK_NAME = new QueryColumn(this, "nick_name");

    /**
     * 联系方式
     */
    public final QueryColumn PHONE_NUM = new QueryColumn(this, "phone_num");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 是否已经设置账号 0:未设置 1:已设置
     */
    public final QueryColumn HAS_ACCOUNT = new QueryColumn(this, "has_account");

    /**
     * 商家用户id
     */
    public final QueryColumn SHOP_USER_ID = new QueryColumn(this, "shop_user_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SHOP_USER_ID, CREATE_TIME, UPDATE_TIME, SHOP_ID, NICK_NAME, CODE, PHONE_NUM, HAS_ACCOUNT};

    public ShopUserTableDef() {
        super("", "shop_user");
    }

}
