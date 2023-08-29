package com.xxw.shop.feign;

import cn.hutool.core.util.StrUtil;
import com.xxw.shop.api.support.feign.OssFeignClient;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.starter.minio.MinioComponent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OssFeignController implements OssFeignClient {

    @Resource
    private MinioComponent minioComponent;

    @Override
    public ServerResponseEntity<String> preview(String fileName) {
        if (StrUtil.isBlank(fileName)) {
            return ServerResponseEntity.success();
        }
        return ServerResponseEntity.success(minioComponent.preview(fileName));
    }
}
