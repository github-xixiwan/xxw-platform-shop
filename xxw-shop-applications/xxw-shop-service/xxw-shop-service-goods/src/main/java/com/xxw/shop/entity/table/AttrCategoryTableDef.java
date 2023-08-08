package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class AttrCategoryTableDef extends TableDef {

    /**
     * 
     */
    public static final AttrCategoryTableDef ATTR_CATEGORY = new AttrCategoryTableDef();

    /**
     * 属性id
     */
    public final QueryColumn ATTR_ID = new QueryColumn(this, "attr_id");

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
     * 属性与分类关联id
     */
    public final QueryColumn ATTR_CATEGORY_ID = new QueryColumn(this, "attr_category_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ATTR_CATEGORY_ID, CREATE_TIME, UPDATE_TIME, CATEGORY_ID, ATTR_ID};

    public AttrCategoryTableDef() {
        super("", "attr_category");
    }

}
