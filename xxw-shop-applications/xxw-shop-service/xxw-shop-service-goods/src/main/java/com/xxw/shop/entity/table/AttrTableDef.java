package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class AttrTableDef extends TableDef {

    /**
     * 
     */
    public static final AttrTableDef ATTR = new AttrTableDef();

    /**
     * 属性描述
     */
    public final QueryColumn DESC = new QueryColumn(this, "desc");

    /**
     * 属性名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * attr id
     */
    public final QueryColumn ATTR_ID = new QueryColumn(this, "attr_id");

    /**
     * 店铺Id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 0:销售属性，1:基本属性
     */
    public final QueryColumn ATTR_TYPE = new QueryColumn(this, "attr_type");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 0:不需要，1:需要
     */
    public final QueryColumn SEARCH_TYPE = new QueryColumn(this, "search_type");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ATTR_ID, CREATE_TIME, UPDATE_TIME, SHOP_ID, NAME, DESC, SEARCH_TYPE, ATTR_TYPE};

    public AttrTableDef() {
        super("", "attr");
    }

}
