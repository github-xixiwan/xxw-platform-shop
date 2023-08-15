package com.xxw.shop.starter.canal.support.parser.converter;

import java.sql.JDBCType;

public class BigIntCanalFieldConverter extends BaseCanalFieldConverter<Long> {

    /**
     * 单例
     */
    public static final BaseCanalFieldConverter<Long> X = new BigIntCanalFieldConverter();

    private BigIntCanalFieldConverter() {
        super(JDBCType.BIGINT, Long.class);
    }

    @Override
    protected Long convertInternal(String source) {
        return Long.valueOf(source);
    }
}
