package com.xxw.shop.starter.canal.support.parser.converter;

public interface BinLogFieldConverter<SOURCE, TARGET> {

    TARGET convert(SOURCE source);
}
