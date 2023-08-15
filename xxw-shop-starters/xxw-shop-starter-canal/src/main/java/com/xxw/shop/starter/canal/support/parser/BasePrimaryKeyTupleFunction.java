package com.xxw.shop.starter.canal.support.parser;

import java.util.Map;

public abstract class BasePrimaryKeyTupleFunction implements TupleFunction<Map<String, String>, Map<String, String>,
        String, Long> {

    @Override
    public Long apply(Map<String, String> before, Map<String, String> after, String primaryKey) {
        throw new UnsupportedOperationException();
    }
}
