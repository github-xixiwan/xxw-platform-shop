package com.xxw.shop.api.support.feign;

import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.feign.FeignInsideAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * oss feign连接
 */
@FeignClient(value = "shop-support", contextId = "OssFeign")
public interface OssFeignClient {

    /**
     * 根据文件名获取预览url
     *
     * @param fileName 文件名
     * @return 预览url
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/oss/preview")
    ServerResponseEntity<String> preview(@RequestParam("fileName") String fileName);

}
