package com.xxw.shop.starter.canal.support.adapter;

public interface SourceAdapter<SOURCE, SINK> {

    SINK adapt(SOURCE source);
}
