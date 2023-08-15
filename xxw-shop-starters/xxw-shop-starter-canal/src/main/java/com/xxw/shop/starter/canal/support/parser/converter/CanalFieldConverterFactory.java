package com.xxw.shop.starter.canal.support.parser.converter;

public interface CanalFieldConverterFactory {

    default void registerConverter(BaseCanalFieldConverter<?> converter) {
        registerConverter(converter, true);
    }

    void registerConverter(BaseCanalFieldConverter<?> converter, boolean replace);

    CanalFieldConvertResult load(CanalFieldConvertInput input);
}
