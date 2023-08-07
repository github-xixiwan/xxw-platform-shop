package com.xxw.shop.module.minio.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-07
 */
public class AttachFileGroupTableDef extends TableDef {

    /**
     * 
     */
    public static final AttachFileGroupTableDef ATTACH_FILE_GROUP = new AttachFileGroupTableDef();

    /**
     * 分组名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 1:图片 2:视频 3:文件
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 店铺id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    
    public final QueryColumn ATTACH_FILE_GROUP_ID = new QueryColumn(this, "attach_file_group_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ATTACH_FILE_GROUP_ID, CREATE_TIME, UPDATE_TIME, SHOP_ID, NAME, TYPE};

    public AttachFileGroupTableDef() {
        super("", "attach_file_group");
    }

}
