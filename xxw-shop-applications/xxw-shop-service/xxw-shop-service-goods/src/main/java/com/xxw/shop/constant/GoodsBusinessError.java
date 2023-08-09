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
