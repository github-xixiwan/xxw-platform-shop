package com.xxw.shop.controller.minio;

import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.minio.dto.AttachFileGroupDTO;
import com.xxw.shop.module.minio.entity.AttachFileGroup;
import com.xxw.shop.module.minio.service.AttachFileGroupService;
import com.xxw.shop.module.minio.vo.AttachFileGroupVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  控制层。
 *
 * @author liaoxiting
 * @since 2023-08-07
 */
@RestController
@RequestMapping("/attach_file_group")
public class AttachFileGroupController {

    @Resource
    private AttachFileGroupService attachFileGroupService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/list")
    @Operation(summary = "获取列表" , description = "分页获取列表")
    public ServerResponseEntity<List<AttachFileGroupVO>> list() {
        List<AttachFileGroupVO> attachFileGroupPage = attachFileGroupService.listByShopId();
        return ServerResponseEntity.success(attachFileGroupPage);
    }

    @GetMapping
    @Operation(summary = "获取" , description = "根据attachFileGroupId获取")
    public ServerResponseEntity<AttachFileGroupVO> getByAttachFileGroupId(@RequestParam Long attachFileGroupId) {
        return ServerResponseEntity.success(attachFileGroupService.getByAttachFileGroupId(attachFileGroupId));
    }

    @PostMapping
    @Operation(summary = "保存" , description = "保存")
    public ServerResponseEntity<Void> save(@Valid @RequestBody AttachFileGroupDTO attachFileGroupDTO) {
        AttachFileGroup attachFileGroup = mapperFacade.map(attachFileGroupDTO, AttachFileGroup.class);
        attachFileGroup.setAttachFileGroupId(null);
        attachFileGroupService.saveAttachFileGroup(attachFileGroup);
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新" , description = "更新")
    public ServerResponseEntity<Void> update(@Valid @RequestBody AttachFileGroupDTO attachFileGroupDTO) {
        AttachFileGroup attachFileGroup = mapperFacade.map(attachFileGroupDTO, AttachFileGroup.class);
        attachFileGroupService.updateById(attachFileGroup);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除" , description = "根据id删除")
    public ServerResponseEntity<Void> delete(@RequestParam Long attachFileGroupId) {
        attachFileGroupService.deleteById(attachFileGroupId);
        return ServerResponseEntity.success();
    }

}
