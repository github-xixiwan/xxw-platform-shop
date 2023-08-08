package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public class SpuTableDef extends TableDef {

    /**
     * 
     */
    public static final SpuTableDef SPU = new SpuTableDef();

    /**
     * 序号
     */
    public final QueryColumn SEQ = new QueryColumn(this, "seq");

    /**
     * 商品名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * spu id
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 商品视频
     */
    public final QueryColumn VIDEO = new QueryColumn(this, "video");

    /**
     * 店铺id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 状态 -1:删除, 0:下架, 1:上架
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 品牌ID
     */
    public final QueryColumn BRAND_ID = new QueryColumn(this, "brand_id");

    /**
     * 商品图片 多个图片逗号分隔
     */
    public final QueryColumn IMG_URLS = new QueryColumn(this, "img_urls");

    /**
     * 售价，整数方式保存
     */
    public final QueryColumn PRICE_FEE = new QueryColumn(this, "price_fee");

    /**
     * sku是否含有图片 0无 1有
     */
    public final QueryColumn HAS_SKU_IMG = new QueryColumn(this, "has_sku_img");

    /**
     * 分类ID
     */
    public final QueryColumn CATEGORY_ID = new QueryColumn(this, "category_id");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 商品介绍主图
     */
    public final QueryColumn MAIN_IMG_URL = new QueryColumn(this, "main_img_url");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 卖点
     */
    public final QueryColumn SELLING_POINT = new QueryColumn(this, "selling_point");

    /**
     * 市场价，整数方式保存
     */
    public final QueryColumn MARKET_PRICE_FEE = new QueryColumn(this, "market_price_fee");

    /**
     * 店铺分类ID
     */
    public final QueryColumn SHOP_CATEGORY_ID = new QueryColumn(this, "shop_category_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SPU_ID, CREATE_TIME, UPDATE_TIME, BRAND_ID, CATEGORY_ID, SHOP_CATEGORY_ID, SHOP_ID, NAME, SELLING_POINT, MAIN_IMG_URL, IMG_URLS, VIDEO, PRICE_FEE, MARKET_PRICE_FEE, STATUS, HAS_SKU_IMG, SEQ};

    public SpuTableDef() {
        super("", "spu");
    }

}
