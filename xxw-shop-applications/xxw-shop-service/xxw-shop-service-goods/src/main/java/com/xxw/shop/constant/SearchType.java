package com.xxw.shop.constant;

/**
 * 属性的搜素类型
 */
public enum SearchType {

    /**
     * 不需要作为搜索参数
     */
    NOT_SEARCH(0),

    /**
     * 搜索参数
     */
    SEARCH(1);

    private final Integer value;

    public Integer value() {
        return value;
    }

    SearchType(Integer value) {
        this.value = value;
    }

}
