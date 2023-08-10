package com.xxw.shop.constant;

/**
 * es当中的index
 */
public enum DataType {

    /**
     * 全部
     */
    ALL(0),

    /**
     * 销售中
     */
    SALE(1),

    /**
     * 已售罄
     */
    SOLD_OUT(2),

    /**
     * 已下架
     */
    DISABLE(3);

    private final Integer value;

    public Integer value() {
        return value;
    }

    DataType(Integer value) {
        this.value = value;
    }
}
