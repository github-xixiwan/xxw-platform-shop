package com.xxw.shop.starter.canal.util;

import java.util.Collection;

public enum CollectionUtils {

    /**
     * 单例
     */
    X;

    public boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    public boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
