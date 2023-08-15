package com.xxw.shop.starter.canal.support.parser;

import java.util.Map;
import java.util.function.Function;

public abstract class BaseCommonEntryFunction<T> implements Function<Map<String, String>, T> {

    @Override
    public T apply(Map<String, String> entry) {
        throw new UnsupportedOperationException();
    }
}
