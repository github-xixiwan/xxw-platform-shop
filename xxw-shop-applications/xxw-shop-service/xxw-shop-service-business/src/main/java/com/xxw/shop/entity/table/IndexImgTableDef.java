package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public class IndexImgTableDef extends TableDef {

    /**
     * 
     */
    public static final IndexImgTableDef INDEX_IMG = new IndexImgTableDef();

    /**
     * 顺序
     */
    public final QueryColumn SEQ = new QueryColumn(this, "seq");

    /**
     * 主键
     */
    public final QueryColumn IMG_ID = new QueryColumn(this, "img_id");

    /**
     * 关联商品id
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 图片
     */
    public final QueryColumn IMG_URL = new QueryColumn(this, "img_url");

    /**
     * 店铺ID
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 状态 1:enable, 0:disable
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 图片类型 0:小程序
     */
    public final QueryColumn IMG_TYPE = new QueryColumn(this, "img_type");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{IMG_ID, CREATE_TIME, UPDATE_TIME, SHOP_ID, IMG_URL, STATUS, SEQ, SPU_ID, IMG_TYPE};

    public IndexImgTableDef() {
        super("", "index_img");
    }

}
