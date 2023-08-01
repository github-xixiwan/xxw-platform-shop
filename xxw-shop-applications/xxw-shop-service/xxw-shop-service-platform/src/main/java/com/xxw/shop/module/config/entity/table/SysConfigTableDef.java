package com.xxw.shop.module.config.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
public class SysConfigTableDef extends TableDef {

    /**
     * 
     */
    public static final SysConfigTableDef SYS_CONFIG = new SysConfigTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * key
     */
    public final QueryColumn PARAM_KEY = new QueryColumn(this, "param_key");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * value
     */
    public final QueryColumn PARAM_VALUE = new QueryColumn(this, "param_value");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CREATE_TIME, UPDATE_TIME, PARAM_KEY, PARAM_VALUE, REMARK};

    public SysConfigTableDef() {
        super("", "sys_config");
    }

}
