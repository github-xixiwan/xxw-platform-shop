package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public class HotSearchTableDef extends TableDef {

    /**
     * 
     */
    public static final HotSearchTableDef HOT_SEARCH = new HotSearchTableDef();

    /**
     * 顺序
     */
    public final QueryColumn SEQ = new QueryColumn(this, "seq");

    /**
     * 热搜标题
     */
    public final QueryColumn TITLE = new QueryColumn(this, "title");

    /**
     * 店铺ID 0为全平台热搜
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 状态 0下线 1上线
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 内容
     */
    public final QueryColumn CONTENT = new QueryColumn(this, "content");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 主键
     */
    public final QueryColumn HOT_SEARCH_ID = new QueryColumn(this, "hot_search_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{HOT_SEARCH_ID, CREATE_TIME, UPDATE_TIME, SHOP_ID, CONTENT, SEQ, STATUS, TITLE};

    public HotSearchTableDef() {
        super("", "hot_search");
    }

}
