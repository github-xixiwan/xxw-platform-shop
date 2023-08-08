package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class CategoryTableDef extends TableDef {

    /**
     * 
     */
    public static final CategoryTableDef CATEGORY = new CategoryTableDef();

    /**
     * 排序
     */
    public final QueryColumn SEQ = new QueryColumn(this, "seq");

    /**
     * 分类描述
     */
    public final QueryColumn DESC = new QueryColumn(this, "desc");

    /**
     * 分类图标
     */
    public final QueryColumn ICON = new QueryColumn(this, "icon");

    /**
     * 分类名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 分类地址{parent_id}-{child_id},...
     */
    public final QueryColumn PATH = new QueryColumn(this, "path");

    /**
     * 分类层级 从0开始
     */
    public final QueryColumn LEVEL = new QueryColumn(this, "level");

    /**
     * 分类的显示图片
     */
    public final QueryColumn IMG_URL = new QueryColumn(this, "img_url");

    /**
     * 店铺id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 父ID
     */
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");

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
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{CATEGORY_ID, CREATE_TIME, UPDATE_TIME, SHOP_ID, PARENT_ID, NAME, DESC, PATH, STATUS, ICON, IMG_URL, LEVEL, SEQ};

    public CategoryTableDef() {
        super("", "category");
    }

}
