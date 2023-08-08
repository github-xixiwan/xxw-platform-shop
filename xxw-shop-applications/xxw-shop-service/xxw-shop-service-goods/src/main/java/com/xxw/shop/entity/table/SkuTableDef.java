package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class SkuTableDef extends TableDef {

    /**
     * 
     */
    public static final SkuTableDef SKU = new SkuTableDef();

    /**
     * 多个销售属性值id逗号分隔
     */
    public final QueryColumn ATTRS = new QueryColumn(this, "attrs");

    /**
     * 属性id
     */
    public final QueryColumn SKU_ID = new QueryColumn(this, "sku_id");

    /**
     * SPU id
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * sku图片
     */
    public final QueryColumn IMG_URL = new QueryColumn(this, "img_url");

    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 商品体积
     */
    public final QueryColumn VOLUME = new QueryColumn(this, "volume");

    /**
     * 商品重量
     */
    public final QueryColumn WEIGHT = new QueryColumn(this, "weight");

    /**
     * 商品条形码
     */
    public final QueryColumn MODEL_ID = new QueryColumn(this, "model_id");

    /**
     * sku名称
     */
    public final QueryColumn SKU_NAME = new QueryColumn(this, "sku_name");

    /**
     * 售价，整数方式保存
     */
    public final QueryColumn PRICE_FEE = new QueryColumn(this, "price_fee");

    /**
     * 商品编码
     */
    public final QueryColumn PARTY_CODE = new QueryColumn(this, "party_code");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 市场价，整数方式保存
     */
    public final QueryColumn MARKET_PRICE_FEE = new QueryColumn(this, "market_price_fee");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SKU_ID, CREATE_TIME, UPDATE_TIME, SPU_ID, SKU_NAME, ATTRS, IMG_URL, PRICE_FEE, MARKET_PRICE_FEE, PARTY_CODE, MODEL_ID, WEIGHT, VOLUME, STATUS};

    public SkuTableDef() {
        super("", "sku");
    }

}
