package com.xxw.shop.starter.canal.support.parser.converter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CanalFieldConvertResult {

    private final BaseCanalFieldConverter<?> converter;
}
