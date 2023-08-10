package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public class OrderAddrTableDef extends TableDef {

    /**
     *
     */
    public static final OrderAddrTableDef ORDER_ADDR = new OrderAddrTableDef();

    /**
     * 纬度
     */
    public final QueryColumn LAT = new QueryColumn(this, "lat");

    /**
     * 经度
     */
    public final QueryColumn LNG = new QueryColumn(this, "lng");

    /**
     * 地址
     */
    public final QueryColumn ADDR = new QueryColumn(this, "addr");

    /**
     * 区
     */
    public final QueryColumn AREA = new QueryColumn(this, "area");

    /**
     * 城市
     */
    public final QueryColumn CITY = new QueryColumn(this, "city");

    /**
     * 区域ID
     */
    public final QueryColumn AREA_ID = new QueryColumn(this, "area_id");

    /**
     * 城市ID
     */
    public final QueryColumn CITY_ID = new QueryColumn(this, "city_id");

    /**
     * 手机
     */
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");

    /**
     * 用户ID
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 邮编
     */
    public final QueryColumn POST_CODE = new QueryColumn(this, "post_code");

    /**
     * 省
     */
    public final QueryColumn PROVINCE = new QueryColumn(this, "province");

    /**
     * 收货人
     */
    public final QueryColumn CONSIGNEE = new QueryColumn(this, "consignee");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 省ID
     */
    public final QueryColumn PROVINCE_ID = new QueryColumn(this, "province_id");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * ID
     */
    public final QueryColumn ORDER_ADDR_ID = new QueryColumn(this, "order_addr_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ORDER_ADDR_ID, CREATE_TIME, UPDATE_TIME, USER_ID,
            CONSIGNEE, PROVINCE_ID, PROVINCE, CITY_ID, CITY, AREA_ID, AREA, ADDR, POST_CODE, MOBILE, LNG, LAT};

    public OrderAddrTableDef() {
        super("", "order_addr");
    }

}
