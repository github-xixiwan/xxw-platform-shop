package com.xxw.shop.constant;

import com.xxw.shop.module.common.exception.ErrorEnumInterface;

/**
 * ORDER业务级抛错
 */
public enum OrderBusinessError implements ErrorEnumInterface {

    ORDER_00001("ORDER_00001", "请填写收货地址"),
    ORDER_00002("ORDER_00002", "获取订单id失败"),
    ORDER_00003("ORDER_00003", "分类名已存在，请重新输入"),
    ORDER_00004("ORDER_00004", "分类不能成为本身的上级分类"),
    ORDER_00005("ORDER_00005", "该分类在使用中，不能进行删除操作"),
    ORDER_00006("ORDER_00006", "商品不存在或者已被删除"),
    ORDER_00007("ORDER_00007", "分类不能为空"),
    ORDER_00008("ORDER_00008", "品牌名称不能为空"),
    ORDER_00009("ORDER_00009", "状态不能为空"),
    ORDER_00010("ORDER_00010", "品牌id不能为空"),
    ORDER_00011("ORDER_00011", "属性类型不能为空"),
    ORDER_00012("ORDER_00012", "关联分类不能为空"),
    ORDER_00013("ORDER_00013", "搜索属性不能为空"),
    ORDER_00014("ORDER_00014", "分类等级最高只能为二级分类"),
    ORDER_00015("ORDER_00015", "店铺分类不能为空"),
    ORDER_00016("ORDER_00016", "平台分类不能为空"),
    ORDER_00017("ORDER_00017", "查找不到该商品信息"),
    ORDER_00018("ORDER_00018", "商品状态异常，清刷新后重试"),
    ORDER_00019("ORDER_00019", "该商品所属的平台分类处于下线中，商品不能上架，请联系管理员后再进行操作"),
    ORDER_00020("ORDER_00020", "该商品所属的店铺分类禁用中，商品不能进行上架操作"),
    ORDER_00021("ORDER_00021", "您选择的商品信息有误，请刷新后重试"),
    ORDER_00022("ORDER_00022", "您所选择的商品中没有符合操作条件的商品"),
    ;

    private String code;

    private String message;

    OrderBusinessError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
