package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class SkuStockTableDef extends TableDef {

    /**
     * 
     */
    public static final SkuStockTableDef SKU_STOCK = new SkuStockTableDef();

    /**
     * SKU ID
     */
    public final QueryColumn SKU_ID = new QueryColumn(this, "sku_id");

    /**
     * 可售卖库存
     */
    public final QueryColumn STOCK = new QueryColumn(this, "stock");

    /**
     * 库存id
     */
    public final QueryColumn STOCK_ID = new QueryColumn(this, "stock_id");

    /**
     * 锁定库存
     */
    public final QueryColumn LOCK_STOCK = new QueryColumn(this, "lock_stock");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 实际库存
     */
    public final QueryColumn ACTUAL_STOCK = new QueryColumn(this, "actual_stock");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{STOCK_ID, CREATE_TIME, UPDATE_TIME, SKU_ID, ACTUAL_STOCK, LOCK_STOCK, STOCK};

    public SkuStockTableDef() {
        super("", "sku_stock");
    }

}
