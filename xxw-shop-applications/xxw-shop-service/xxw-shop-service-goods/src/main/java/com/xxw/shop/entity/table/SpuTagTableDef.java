package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class SpuTagTableDef extends TableDef {

    /**
     * 
     */
    public static final SpuTagTableDef SPU_TAG = new SpuTagTableDef();

    /**
     * 分组标签id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 排序
     */
    public final QueryColumn SEQ = new QueryColumn(this, "seq");

    /**
     * 列表样式(0:一列一个,1:一列两个,2:一列三个)
     */
    public final QueryColumn STYLE = new QueryColumn(this, "style");

    /**
     * 分组标题
     */
    public final QueryColumn TITLE = new QueryColumn(this, "title");

    /**
     * 店铺Id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 状态(1为正常,-1为删除)
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 默认类型(0:商家自定义,1:系统默认)
     */
    public final QueryColumn IS_DEFAULT = new QueryColumn(this, "is_default");

    /**
     * 商品数量
     */
    public final QueryColumn PROD_COUNT = new QueryColumn(this, "prod_count");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 删除时间
     */
    public final QueryColumn DELETE_TIME = new QueryColumn(this, "delete_time");

    /**
     * 修改时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CREATE_TIME, UPDATE_TIME, TITLE, SHOP_ID, STATUS, IS_DEFAULT, PROD_COUNT, STYLE, SEQ, DELETE_TIME};

    public SpuTagTableDef() {
        super("", "spu_tag");
    }

}
