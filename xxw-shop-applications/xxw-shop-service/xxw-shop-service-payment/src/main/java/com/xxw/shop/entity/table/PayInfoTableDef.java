package com.xxw.shop.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 *  表定义层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public class PayInfoTableDef extends TableDef {

    /**
     * 
     */
    public static final PayInfoTableDef PAY_INFO = new PayInfoTableDef();

    /**
     * 支付单号
     */
    public final QueryColumn PAY_ID = new QueryColumn(this, "pay_id");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 系统类型 见SysTypeEnum
     */
    public final QueryColumn SYS_TYPE = new QueryColumn(this, "sys_type");

    /**
     * 版本号
     */
    public final QueryColumn VERSION = new QueryColumn(this, "version");

    /**
     * 外部订单流水号
     */
    public final QueryColumn BIZ_PAY_NO = new QueryColumn(this, "biz_pay_no");

    /**
     * 本次支付关联的多个订单号
     */
    public final QueryColumn ORDER_IDS = new QueryColumn(this, "order_ids");

    /**
     * 支付金额
     */
    public final QueryColumn PAY_AMOUNT = new QueryColumn(this, "pay_amount");

    /**
     * 支付状态
     */
    public final QueryColumn PAY_STATUS = new QueryColumn(this, "pay_status");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 确认时间
     */
    public final QueryColumn CONFIRM_TIME = new QueryColumn(this, "confirm_time");

    /**
     * 回调时间
     */
    public final QueryColumn CALLBACK_TIME = new QueryColumn(this, "callback_time");

    /**
     * 回调内容
     */
    public final QueryColumn CALLBACK_CONTENT = new QueryColumn(this, "callback_content");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{PAY_ID, CREATE_TIME, UPDATE_TIME, USER_ID, ORDER_IDS, BIZ_PAY_NO, SYS_TYPE, PAY_STATUS, PAY_AMOUNT, VERSION, CALLBACK_CONTENT, CALLBACK_TIME, CONFIRM_TIME};

    public PayInfoTableDef() {
        super("", "pay_info");
    }

}
