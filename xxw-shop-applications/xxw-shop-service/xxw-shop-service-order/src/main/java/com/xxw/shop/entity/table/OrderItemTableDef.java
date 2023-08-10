package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public class OrderItemTableDef extends TableDef {

    /**
     *
     */
    public static final OrderItemTableDef ORDER_ITEM = new OrderItemTableDef();

    /**
     * 产品主图片路径
     */
    public final QueryColumn PIC = new QueryColumn(this, "pic");

    /**
     * 购物车产品个数
     */
    public final QueryColumn COUNT = new QueryColumn(this, "count");

    /**
     * 产品价格
     */
    public final QueryColumn PRICE = new QueryColumn(this, "price");

    /**
     * 产品SkuID
     */
    public final QueryColumn SKU_ID = new QueryColumn(this, "sku_id");

    /**
     * 产品ID
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 店铺id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 用户Id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 订单id
     */
    public final QueryColumn ORDER_ID = new QueryColumn(this, "order_id");

    /**
     * sku名称
     */
    public final QueryColumn SKU_NAME = new QueryColumn(this, "sku_name");

    /**
     * 产品名称
     */
    public final QueryColumn SPU_NAME = new QueryColumn(this, "spu_name");

    /**
     * 分类id
     */
    public final QueryColumn CATEGORY_ID = new QueryColumn(this, "category_id");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 订单项ID
     */
    public final QueryColumn ORDER_ITEM_ID = new QueryColumn(this, "order_item_id");

    /**
     * 单个orderItem的配送类型3：无需快递
     */
    public final QueryColumn DELIVERY_TYPE = new QueryColumn(this, "delivery_type");

    /**
     * 加入购物车时间
     */
    public final QueryColumn SHOP_CART_TIME = new QueryColumn(this, "shop_cart_time");

    /**
     * 商品总金额
     */
    public final QueryColumn SPU_TOTAL_AMOUNT = new QueryColumn(this, "spu_total_amount");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ORDER_ITEM_ID, CREATE_TIME, UPDATE_TIME, SHOP_ID,
            ORDER_ID, CATEGORY_ID, SPU_ID, SKU_ID, USER_ID, COUNT, SPU_NAME, SKU_NAME, PIC, DELIVERY_TYPE,
            SHOP_CART_TIME, PRICE, SPU_TOTAL_AMOUNT};

    public OrderItemTableDef() {
        super("", "order_item");
    }

}
