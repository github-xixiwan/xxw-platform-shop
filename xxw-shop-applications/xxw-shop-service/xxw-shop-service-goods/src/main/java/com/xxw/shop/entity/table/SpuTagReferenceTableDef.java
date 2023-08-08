package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class SpuTagReferenceTableDef extends TableDef {

    /**
     * 
     */
    public static final SpuTagReferenceTableDef SPU_TAG_REFERENCE = new SpuTagReferenceTableDef();

    /**
     * 排序
     */
    public final QueryColumn SEQ = new QueryColumn(this, "seq");

    /**
     * 商品id
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 标签id
     */
    public final QueryColumn TAG_ID = new QueryColumn(this, "tag_id");

    /**
     * 店铺id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 状态(1:正常,-1:删除)
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 分组引用id
     */
    public final QueryColumn REFERENCE_ID = new QueryColumn(this, "reference_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{REFERENCE_ID, CREATE_TIME, UPDATE_TIME, SHOP_ID, TAG_ID, SPU_ID, STATUS, SEQ};

    public SpuTagReferenceTableDef() {
        super("", "spu_tag_reference");
    }

}
