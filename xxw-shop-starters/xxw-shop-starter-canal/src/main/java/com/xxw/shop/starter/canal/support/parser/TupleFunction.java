package com.xxw.shop.starter.canal.support.parser;

@FunctionalInterface
public interface TupleFunction<BEFORE, AFTER, KEY, R> {

    R apply(BEFORE before, AFTER after, KEY key);
}
