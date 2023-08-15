package com.xxw.shop.starter.canal.support.adapter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE, staticName = "of")
class RawStringSourceAdapter implements SourceAdapter<String, String> {

    @Override
    public String adapt(String source) {
        return source;
    }
}
