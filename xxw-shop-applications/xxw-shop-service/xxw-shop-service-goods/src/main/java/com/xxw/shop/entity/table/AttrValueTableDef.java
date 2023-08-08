package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class AttrValueTableDef extends TableDef {

    /**
     * 
     */
    public static final AttrValueTableDef ATTR_VALUE = new AttrValueTableDef();

    /**
     * 属性值
     */
    public final QueryColumn VALUE = new QueryColumn(this, "value");

    /**
     * 属性ID
     */
    public final QueryColumn ATTR_ID = new QueryColumn(this, "attr_id");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 属性id
     */
    public final QueryColumn ATTR_VALUE_ID = new QueryColumn(this, "attr_value_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ATTR_VALUE_ID, CREATE_TIME, UPDATE_TIME, ATTR_ID, VALUE};

    public AttrValueTableDef() {
        super("", "attr_value");
    }

}
