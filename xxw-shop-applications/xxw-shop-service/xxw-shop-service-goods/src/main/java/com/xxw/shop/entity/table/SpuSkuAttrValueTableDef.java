package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class SpuSkuAttrValueTableDef extends TableDef {

    /**
     * 
     */
    public static final SpuSkuAttrValueTableDef SPU_SKU_ATTR_VALUE = new SpuSkuAttrValueTableDef();

    /**
     * SKU ID
     */
    public final QueryColumn SKU_ID = new QueryColumn(this, "sku_id");

    /**
     * SPU ID
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 销售属性ID
     */
    public final QueryColumn ATTR_ID = new QueryColumn(this, "attr_id");

    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 销售属性名称
     */
    public final QueryColumn ATTR_NAME = new QueryColumn(this, "attr_name");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 销售属性值ID
     */
    public final QueryColumn ATTR_VALUE_ID = new QueryColumn(this, "attr_value_id");

    /**
     * 商品sku销售属性关联信息id
     */
    public final QueryColumn SPU_SKU_ATTR_ID = new QueryColumn(this, "spu_sku_attr_id");

    /**
     * 销售属性值
     */
    public final QueryColumn ATTR_VALUE_NAME = new QueryColumn(this, "attr_value_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SPU_SKU_ATTR_ID, CREATE_TIME, UPDATE_TIME, SPU_ID, SKU_ID, ATTR_ID, ATTR_NAME, ATTR_VALUE_ID, ATTR_VALUE_NAME, STATUS};

    public SpuSkuAttrValueTableDef() {
        super("", "spu_sku_attr_value");
    }

}
