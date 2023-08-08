package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class SkuStockLockTableDef extends TableDef {

    /**
     * 
     */
    public static final SkuStockLockTableDef SKU_STOCK_LOCK = new SkuStockLockTableDef();

    /**
     * id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 锁定库存数量
     */
    public final QueryColumn COUNT = new QueryColumn(this, "count");

    /**
     * sku id
     */
    public final QueryColumn SKU_ID = new QueryColumn(this, "sku_id");

    /**
     * 商品id
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 状态-1已解锁 0待确定 1已锁定
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 订单id
     */
    public final QueryColumn ORDER_ID = new QueryColumn(this, "order_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CREATE_TIME, UPDATE_TIME, SPU_ID, SKU_ID, ORDER_ID, STATUS, COUNT};

    public SkuStockLockTableDef() {
        super("", "sku_stock_lock");
    }

}
