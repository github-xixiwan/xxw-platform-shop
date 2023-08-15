package com.xxw.shop.canal;

import cn.hutool.core.collection.CollectionUtil;
import com.xxw.shop.starter.canal.CanalGlue;
import com.xxw.shop.starter.canal.model.CanalBinLogEvent;
import com.xxw.shop.starter.canal.model.ModelTable;
import com.xxw.shop.starter.canal.support.adapter.SourceAdapterFacade;
import com.xxw.shop.starter.canal.support.processor.BaseCanalBinlogEventProcessor;
import com.xxw.shop.starter.canal.support.processor.CanalBinlogEventProcessorFactory;

import java.util.List;

public class ShopCanalGlue implements CanalGlue {

    private final CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory;

    @Override
    public void process(String content) {
        CanalBinLogEvent event = (CanalBinLogEvent) SourceAdapterFacade.X.adapt(CanalBinLogEvent.class, content);
        ModelTable modelTable = ModelTable.of(event.getDatabase(), event.getTable());
        List<BaseCanalBinlogEventProcessor<?>> baseCanalBinlogEventProcessors =
                this.canalBinlogEventProcessorFactory.get(modelTable);
        if (CollectionUtil.isEmpty(baseCanalBinlogEventProcessors)) {
            return;
        }
        baseCanalBinlogEventProcessors.forEach((processor) -> {
            processor.process(event);
        });
    }


    private ShopCanalGlue(CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory) {
        this.canalBinlogEventProcessorFactory = canalBinlogEventProcessorFactory;
    }

    public static ShopCanalGlue of(CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory) {
        return new ShopCanalGlue(canalBinlogEventProcessorFactory);
    }
}
