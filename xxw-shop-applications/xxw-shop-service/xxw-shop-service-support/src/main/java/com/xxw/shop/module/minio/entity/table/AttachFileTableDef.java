package com.xxw.shop.module.minio.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-07
 */
public class AttachFileTableDef extends TableDef {

    /**
     * 
     */
    public static final AttachFileTableDef ATTACH_FILE = new AttachFileTableDef();

    /**
     * 文件 1:图片 2:视频 3:文件
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    
    public final QueryColumn FILE_ID = new QueryColumn(this, "file_id");

    /**
     * 店铺id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 文件名
     */
    public final QueryColumn FILE_NAME = new QueryColumn(this, "file_name");

    /**
     * 文件路径
     */
    public final QueryColumn FILE_PATH = new QueryColumn(this, "file_path");

    /**
     * 文件大小
     */
    public final QueryColumn FILE_SIZE = new QueryColumn(this, "file_size");

    /**
     * 文件类型
     */
    public final QueryColumn FILE_TYPE = new QueryColumn(this, "file_type");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 文件分组id
     */
    public final QueryColumn ATTACH_FILE_GROUP_ID = new QueryColumn(this, "attach_file_group_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{FILE_ID, CREATE_TIME, UPDATE_TIME, FILE_PATH, FILE_TYPE, FILE_NAME, FILE_SIZE, SHOP_ID, TYPE, ATTACH_FILE_GROUP_ID};

    public AttachFileTableDef() {
        super("", "attach_file");
    }

}
