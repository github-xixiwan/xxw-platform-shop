package com.xxw.shop.starter.canal.support.parser;

import com.xxw.shop.starter.canal.support.parser.converter.BaseCanalFieldConverter;
import lombok.Data;

@Data
public class ColumnMetadata {

    private String columnName;

    private BaseCanalFieldConverter<?> converter;
}
