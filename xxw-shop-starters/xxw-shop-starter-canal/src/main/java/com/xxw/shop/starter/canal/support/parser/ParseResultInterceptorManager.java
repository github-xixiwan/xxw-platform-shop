package com.xxw.shop.starter.canal.support.parser;

import java.util.List;

public interface ParseResultInterceptorManager {

    <T> void registerParseResultInterceptor(BaseParseResultInterceptor<T> parseResultInterceptor);

    <T> List<BaseParseResultInterceptor<T>> getParseResultInterceptors(Class<T> klass);
}
