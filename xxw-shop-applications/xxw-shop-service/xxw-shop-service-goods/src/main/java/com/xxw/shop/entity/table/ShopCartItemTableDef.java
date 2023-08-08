package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class ShopCartItemTableDef extends TableDef {

    /**
     * 
     */
    public static final ShopCartItemTableDef SHOP_CART_ITEM = new ShopCartItemTableDef();

    /**
     * 购物车产品个数
     */
    public final QueryColumn COUNT = new QueryColumn(this, "count");

    /**
     * SkuID
     */
    public final QueryColumn SKU_ID = new QueryColumn(this, "sku_id");

    /**
     * 产品ID
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 店铺ID
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 用户ID
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 售价，加入购物车时的商品价格
     */
    public final QueryColumn PRICE_FEE = new QueryColumn(this, "price_fee");

    /**
     * 是否已勾选
     */
    public final QueryColumn IS_CHECKED = new QueryColumn(this, "is_checked");

    /**
     * 主键
     */
    public final QueryColumn CART_ITEM_ID = new QueryColumn(this, "cart_item_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{CART_ITEM_ID, CREATE_TIME, UPDATE_TIME, SHOP_ID, SPU_ID, SKU_ID, USER_ID, COUNT, PRICE_FEE, IS_CHECKED};

    public ShopCartItemTableDef() {
        super("", "shop_cart_item");
    }

}
