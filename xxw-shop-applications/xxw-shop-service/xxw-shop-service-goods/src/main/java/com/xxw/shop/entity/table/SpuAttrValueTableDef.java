package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class SpuAttrValueTableDef extends TableDef {

    /**
     * 
     */
    public static final SpuAttrValueTableDef SPU_ATTR_VALUE = new SpuAttrValueTableDef();

    /**
     * 商品id
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 规格属性id
     */
    public final QueryColumn ATTR_ID = new QueryColumn(this, "attr_id");

    /**
     * 规格属性描述
     */
    public final QueryColumn ATTR_DESC = new QueryColumn(this, "attr_desc");

    /**
     * 规格属性名称
     */
    public final QueryColumn ATTR_NAME = new QueryColumn(this, "attr_name");

    /**
     * 规格属性值id
     */
    public final QueryColumn ATTR_VALUE_ID = new QueryColumn(this, "attr_value_id");

    /**
     * 规格属性值名称
     */
    public final QueryColumn ATTR_VALUE_NAME = new QueryColumn(this, "attr_value_name");

    /**
     * 商品属性值关联信息id
     */
    public final QueryColumn SPU_ATTR_VALUE_ID = new QueryColumn(this, "spu_attr_value_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SPU_ATTR_VALUE_ID, SPU_ID, ATTR_ID, ATTR_NAME, ATTR_VALUE_ID, ATTR_VALUE_NAME, ATTR_DESC};

    public SpuAttrValueTableDef() {
        super("", "spu_attr_value");
    }

}
