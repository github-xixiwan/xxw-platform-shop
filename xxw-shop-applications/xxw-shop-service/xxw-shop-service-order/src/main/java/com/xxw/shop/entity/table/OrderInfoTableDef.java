package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public class OrderInfoTableDef extends TableDef {

    /**
     *
     */
    public static final OrderInfoTableDef ORDER_INFO = new OrderInfoTableDef();

    /**
     * 总值
     */
    public final QueryColumn TOTAL = new QueryColumn(this, "total");

    /**
     * 店铺id
     */
    public final QueryColumn SHOP_ID = new QueryColumn(this, "shop_id");

    /**
     * 订单状态 1:待付款 2:待发货 3:待收货(已发货) 5:成功 6:失败
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 用户ID
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 是否已支付，1.已支付0.未支付
     */
    public final QueryColumn IS_PAYED = new QueryColumn(this, "is_payed");

    /**
     * 订单ID
     */
    public final QueryColumn ORDER_ID = new QueryColumn(this, "order_id");

    /**
     * 付款时间
     */
    public final QueryColumn PAY_TIME = new QueryColumn(this, "pay_time");

    /**
     * 订单版本号，每处理一次订单，版本号+1
     */
    public final QueryColumn VERSION = new QueryColumn(this, "version");

    /**
     * 订单商品总数
     */
    public final QueryColumn ALL_COUNT = new QueryColumn(this, "all_count");

    /**
     * 店铺名称
     */
    public final QueryColumn SHOP_NAME = new QueryColumn(this, "shop_name");

    /**
     * 订单关闭原因 1-超时未支付 4-买家取消 15-已通过货到付款交易
     */
    public final QueryColumn CLOSE_TYPE = new QueryColumn(this, "close_type");

    /**
     * 取消时间
     */
    public final QueryColumn CANCEL_TIME = new QueryColumn(this, "cancel_time");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 完成时间
     */
    public final QueryColumn FINALLY_TIME = new QueryColumn(this, "finally_time");

    /**
     * 用户订单地址id
     */
    public final QueryColumn ORDER_ADDR_ID = new QueryColumn(this, "order_addr_id");

    /**
     * 结算时间
     */
    public final QueryColumn SETTLED_TIME = new QueryColumn(this, "settled_time");

    /**
     * 用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除
     */
    public final QueryColumn DELETE_STATUS = new QueryColumn(this, "delete_status");

    /**
     * 发货时间
     */
    public final QueryColumn DELIVERY_TIME = new QueryColumn(this, "delivery_time");

    /**
     * 配送类型：无需快递
     */
    public final QueryColumn DELIVERY_TYPE = new QueryColumn(this, "delivery_type");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ORDER_ID, CREATE_TIME, UPDATE_TIME, SHOP_ID,
            USER_ID, DELIVERY_TYPE, SHOP_NAME, TOTAL, STATUS, ALL_COUNT, PAY_TIME, DELIVERY_TIME, FINALLY_TIME,
            SETTLED_TIME, CANCEL_TIME, IS_PAYED, CLOSE_TYPE, DELETE_STATUS, VERSION, ORDER_ADDR_ID};

    public OrderInfoTableDef() {
        super("", "order_info");
    }

}
