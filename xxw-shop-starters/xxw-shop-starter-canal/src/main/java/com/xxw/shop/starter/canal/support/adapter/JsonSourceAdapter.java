package com.xxw.shop.starter.canal.support.adapter;

import com.xxw.shop.module.common.json.JsonUtil;
import com.xxw.shop.starter.canal.util.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE, staticName = "of")
class JsonSourceAdapter<T> implements SourceAdapter<String, T> {

    private final Class<T> klass;

    @Override
    public T adapt(String source) {
        if (StringUtils.X.isEmpty(source)) {
            return null;
        }
        return JsonUtil.fromJson(source, klass);
    }
}
