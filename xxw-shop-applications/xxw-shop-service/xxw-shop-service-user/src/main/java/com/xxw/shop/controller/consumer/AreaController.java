package com.xxw.shop.controller.consumer;

import com.xxw.shop.api.user.vo.AreaVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 省市区地区信息
 */
@RestController("consumerAreaController")
@RequestMapping("/area")
@Tag(name = "consumer-地区信息")
public class AreaController {

    @Resource
    private AreaService areaService;

    @GetMapping("/list")
    @Operation(summary = "获取省市区地区信息列表", description = "获取省市区地区信息列表")
    public ServerResponseEntity<List<AreaVO>> list() {
        List<AreaVO> list = areaService.getAreaListInfo();
        return ServerResponseEntity.success(list);
    }

    @GetMapping("/list_by_pid")
    @Operation(summary = "通过父级id获取区域列表", description = "通过父级id获取区域列表")
    public ServerResponseEntity<List<AreaVO>> listByPid(Long pid) {
        List<AreaVO> list = areaService.listByPid(pid);
        return ServerResponseEntity.success(list);
    }
}
