package com.xxw.shop.controller.config;

import com.xxw.shop.module.config.entity.SysConfig;
import com.xxw.shop.module.config.service.SysConfigService;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 控制层。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
@RestController
@RequestMapping("/sys_config")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 获取保存支付宝支付配置信息
     */
    @GetMapping("/info/{key}")
    public ServerResponseEntity<String> info(@PathVariable("key") String key) {
        return ServerResponseEntity.success(sysConfigService.getValue(key));
    }

    /**
     * 保存配置
     */
    @PostMapping("/save")
    public ServerResponseEntity<Void> save(@RequestBody @Valid SysConfig sysConfig) {
        sysConfigService.saveOrUpdateSysConfig(sysConfig);
        return ServerResponseEntity.success();
    }

}
