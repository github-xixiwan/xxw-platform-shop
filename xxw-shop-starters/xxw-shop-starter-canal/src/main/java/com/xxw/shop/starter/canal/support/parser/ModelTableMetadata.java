package com.xxw.shop.starter.canal.support.parser;

import com.xxw.shop.starter.canal.model.ModelTable;
import lombok.Data;

import java.util.Map;

@Data
public class ModelTableMetadata {

    private ModelTable modelTable;

    /**
     * fieldName -> ColumnMetadata
     */
    private Map<String, ColumnMetadata> fieldColumnMapping;
}
