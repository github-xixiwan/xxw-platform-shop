package com.xxw.shop.starter.canal.model;

public interface ModelTable {

    String database();

    String table();

    static ModelTable of(String database, String table) {
        return DefaultModelTable.of(database, table);
    }
}
