package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public class ShopDetailTableDef extends TableDef {

    /**
     * 
     */
    public static final ShopDetailTableDef SHOP_DETAIL = new ShopDetailTableDef();

    /**
     * 店铺类型1自营店 2普通店
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 店铺简介
     */
    public final QueryColumn INTRO = new QueryColumn(this, "intro");

    /**
     * 店铺id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 店铺logo(可修改)
     */
    public final QueryColumn SHOP_LOGO = new QueryColumn(this, "shop_logo");

    /**
     * 店铺名称
     */
    public final QueryColumn SHOP_NAME = new QueryColumn(this, "shop_name");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 店铺状态(-1:已删除 0: 停业中 1:营业中)
     */
    public final QueryColumn SHOP_STATUS = new QueryColumn(this, "shop_status");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 营业执照
     */
    public final QueryColumn BUSINESS_LICENSE = new QueryColumn(this, "business_license");

    /**
     * 身份证正面
     */
    public final QueryColumn IDENTITY_CARD_FRONT = new QueryColumn(this, "identity_card_front");

    /**
     * 身份证反面
     */
    public final QueryColumn IDENTITY_CARD_LATER = new QueryColumn(this, "identity_card_later");

    /**
     * 店铺移动端背景图
     */
    public final QueryColumn MOBILE_BACKGROUND_PIC = new QueryColumn(this, "mobile_background_pic");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SHOP_ID, CREATE_TIME, UPDATE_TIME, SHOP_NAME, INTRO, SHOP_LOGO, MOBILE_BACKGROUND_PIC, SHOP_STATUS, BUSINESS_LICENSE, IDENTITY_CARD_FRONT, IDENTITY_CARD_LATER, TYPE};

    public ShopDetailTableDef() {
        super("", "shop_detail");
    }

}
