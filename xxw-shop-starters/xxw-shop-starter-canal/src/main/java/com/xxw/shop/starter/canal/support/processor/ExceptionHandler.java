package com.xxw.shop.starter.canal.support.processor;

import com.xxw.shop.starter.canal.model.CanalBinLogEvent;

@FunctionalInterface
public interface ExceptionHandler {

    void onError(CanalBinLogEvent event, Throwable throwable);

    ExceptionHandler NO_OP = (event, throwable) -> {
    };
}
