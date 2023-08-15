package com.xxw.shop.starter.canal;

import com.xxw.shop.starter.canal.model.CanalBinLogEvent;
import com.xxw.shop.starter.canal.model.ModelTable;
import com.xxw.shop.starter.canal.support.adapter.SourceAdapterFacade;
import com.xxw.shop.starter.canal.support.processor.CanalBinlogEventProcessorFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
public class DefaultCanalGlue implements CanalGlue {

    private final CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory;

    @Override
    public void process(String content) {
        CanalBinLogEvent event = SourceAdapterFacade.X.adapt(CanalBinLogEvent.class, content);
        ModelTable modelTable = ModelTable.of(event.getDatabase(), event.getTable());
        canalBinlogEventProcessorFactory.get(modelTable).forEach(processor -> processor.process(event));
    }
}
