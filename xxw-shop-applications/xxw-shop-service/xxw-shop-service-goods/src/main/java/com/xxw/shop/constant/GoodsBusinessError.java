package com.xxw.shop.constant;

import com.xxw.shop.module.common.exception.ErrorEnumInterface;

/**
 * GOODS业务级抛错
 */
public enum GoodsBusinessError implements ErrorEnumInterface {

    GOODS_00001("GOODS_00001", "属性不存在或已删除"),
    GOODS_00002("GOODS_00002", "有部分商品在使用该品牌，不能进行删除操作"),
    GOODS_00003("GOODS_00003", "分类名已存在，请重新输入"),
    GOODS_00004("GOODS_00004", "分类不能成为本身的上级分类"),
    GOODS_00005("GOODS_00005", "该分类在使用中，不能进行删除操作"),
    GOODS_00006("GOODS_00006", "商品不存在或者已被删除"),
    GOODS_00007("GOODS_00007", "分类不能为空"),
    GOODS_00008("GOODS_00008", "品牌名称不能为空"),
    GOODS_00009("GOODS_00009", "状态不能为空"),
    GOODS_00010("GOODS_00010", "品牌id不能为空"),
    GOODS_00011("GOODS_00011", "属性类型不能为空"),
    GOODS_00012("GOODS_00012", "关联分类不能为空"),
    GOODS_00013("GOODS_00013", "搜索属性不能为空"),
    GOODS_00014("GOODS_00014", "分类等级最高只能为二级分类"),
    GOODS_00015("GOODS_00015", "店铺分类不能为空"),
    GOODS_00016("GOODS_00016", "平台分类不能为空"),
    GOODS_00017("GOODS_00017", "查找不到该商品信息"),
    GOODS_00018("GOODS_00018", "商品状态异常，清刷新后重试"),
    GOODS_00019("GOODS_00019", "该商品所属的平台分类处于下线中，商品不能上架，请联系管理员后再进行操作"),
    GOODS_00020("GOODS_00020", "该商品所属的店铺分类禁用中，商品不能进行上架操作"),
    GOODS_00021("GOODS_00021", "您选择的商品信息有误，请刷新后重试"),
    GOODS_00022("GOODS_00022", "您所选择的商品中没有符合操作条件的商品"),
    ;

    private String code;

    private String message;

    GoodsBusinessError(String code, String message) {
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
