package com.xxw.shop.api.support.serializer;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.xxw.shop.api.support.feign.OssFeignClient;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.common.string.PrincipalUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImgJsonSerializer extends JsonSerializer<String> {

    @Resource
    private OssFeignClient ossFeignClient;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (StrUtil.isBlank(value)) {
            gen.writeString(StrUtil.EMPTY);
            return;
        }
        String[] imgs = value.split(StrUtil.COMMA);
        StringBuilder sb = new StringBuilder();

        for (String img : imgs) {
            // 图片为http协议开头，直接返回
            if (PrincipalUtil.isHttpProtocol(img)) {
                sb.append(img).append(StrUtil.COMMA);
            } else {
                ServerResponseEntity<String> serverResponseEntity = ossFeignClient.preview(img);
                String data = serverResponseEntity.getData();
                if (serverResponseEntity.isSuccess() && StrUtil.isNotBlank(data)) {
                    sb.append(data).append(StrUtil.COMMA);
                } else {
                    sb.append(img).append(StrUtil.COMMA);
                }
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        gen.writeString(sb.toString());
    }
}
