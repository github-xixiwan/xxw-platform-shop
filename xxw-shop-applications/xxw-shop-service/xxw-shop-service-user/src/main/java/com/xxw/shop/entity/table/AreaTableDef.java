package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-11
 */
public class AreaTableDef extends TableDef {

    /**
     * 
     */
    public static final AreaTableDef AREA = new AreaTableDef();

    /**
     * 等级（从1开始）
     */
    public final QueryColumn LEVEL = new QueryColumn(this, "level");

    
    public final QueryColumn AREA_ID = new QueryColumn(this, "area_id");

    /**
     * 地址
     */
    public final QueryColumn AREA_NAME = new QueryColumn(this, "area_name");

    /**
     * 上级地址
     */
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{AREA_ID, CREATE_TIME, UPDATE_TIME, AREA_NAME, PARENT_ID, LEVEL};

    public AreaTableDef() {
        super("", "area");
    }

}
