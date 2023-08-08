package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class SpuExtensionTableDef extends TableDef {

    /**
     * 
     */
    public static final SpuExtensionTableDef SPU_EXTENSION = new SpuExtensionTableDef();

    /**
     * 商品id
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 可售卖库存
     */
    public final QueryColumn STOCK = new QueryColumn(this, "stock");

    /**
     * 销量
     */
    public final QueryColumn SALE_NUM = new QueryColumn(this, "sale_num");

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
     * 商品扩展信息表id
     */
    public final QueryColumn SPU_EXTEND_ID = new QueryColumn(this, "spu_extend_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SPU_EXTEND_ID, CREATE_TIME, UPDATE_TIME, SPU_ID, SALE_NUM, ACTUAL_STOCK, LOCK_STOCK, STOCK};

    public SpuExtensionTableDef() {
        super("", "spu_extension");
    }

}
