package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class BrandTableDef extends TableDef {

    /**
     * 
     */
    public static final BrandTableDef BRAND = new BrandTableDef();

    /**
     * 排序
     */
    public final QueryColumn SEQ = new QueryColumn(this, "seq");

    /**
     * 品牌描述
     */
    public final QueryColumn DESC = new QueryColumn(this, "desc");

    /**
     * 品牌名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 品牌logo图片
     */
    public final QueryColumn IMG_URL = new QueryColumn(this, "img_url");

    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * brand_id
     */
    public final QueryColumn BRAND_ID = new QueryColumn(this, "brand_id");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 检索首字母
     */
    public final QueryColumn FIRST_LETTER = new QueryColumn(this, "first_letter");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{BRAND_ID, CREATE_TIME, UPDATE_TIME, NAME, DESC, IMG_URL, FIRST_LETTER, SEQ, STATUS};

    public BrandTableDef() {
        super("", "brand");
    }

}
