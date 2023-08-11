package com.xxw.shop.controller.consumer;

import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.AttrService;
import com.xxw.shop.vo.AttrCompleteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 属性信息
 */
@RestController("consumerAttrController")
@RequestMapping("/ua/attr")
@Tag(name = "app-属性信息")
public class AttrController {

    @Resource
    private AttrService attrService;

    @GetMapping
    @Operation(summary = "获取属性信息", description = "根据attrId获取属性信息")
    public ServerResponseEntity<AttrCompleteVO> getByAttrId(@RequestParam Long attrId) {
        return ServerResponseEntity.success(attrService.getByAttrId(attrId));
    }
}
