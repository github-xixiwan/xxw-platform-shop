package com.xxw.shop.api.platform.feign;

import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.feign.FeignInsideAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "shop-platform", contextId = "configFeign")
public interface ConfigFeignClient {

    /**
     * 获取配置信息
     *
     * @param key key
     * @return 配置信息json
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/config/getConfig")
    ServerResponseEntity<String> getConfig(@RequestParam("key") String key);

}
