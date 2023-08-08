package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class SpuDetailTableDef extends TableDef {

    /**
     * 
     */
    public static final SpuDetailTableDef SPU_DETAIL = new SpuDetailTableDef();

    /**
     * 商品id
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 商品详情
     */
    public final QueryColumn DETAIL = new QueryColumn(this, "detail");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SPU_ID, CREATE_TIME, UPDATE_TIME, DETAIL};

    public SpuDetailTableDef() {
        super("", "spu_detail");
    }

}
