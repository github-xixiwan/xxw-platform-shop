package com.xxw.shop.starter.canal.support.processor;

import com.xxw.shop.starter.canal.model.ModelTable;

import java.util.List;

public interface CanalBinlogEventProcessorFactory {

    void register(ModelTable modelTable, BaseCanalBinlogEventProcessor<?> processor);

    List<BaseCanalBinlogEventProcessor<?>> get(ModelTable modelTable);
}
