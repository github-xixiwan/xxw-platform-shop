package com.xxw.shop.controller.minio;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.constant.SupportBusinessError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.minio.dto.AttachFileDTO;
import com.xxw.shop.module.minio.dto.AttachFileQueryDTO;
import com.xxw.shop.module.minio.entity.AttachFile;
import com.xxw.shop.module.minio.service.AttachFileService;
import com.xxw.shop.module.minio.vo.AttachFileVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 控制层。
 *
 * @author liaoxiting
 * @since 2023-08-07
 */
@RestController
@RequestMapping("/attach_file")
public class AttachFileController {

    @Resource
    private AttachFileService attachFileService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/page")
    @Operation(summary = "获取上传文件记录表列表", description = "分页获取上传文件记录表列表")
    public ServerResponseEntity<Page<AttachFileVO>> page(@Valid AttachFileQueryDTO dto, String fileName,
                                                         Long fileGroupId) {
        if (fileGroupId == 0) {
            fileGroupId = null;
        }
        dto.setFileName(fileName);
        dto.setFileGroupId(fileGroupId);
        return ServerResponseEntity.success(attachFileService.page(dto));
    }

    @PostMapping
    @Operation(summary = "保存上传文件记录", description = "保存上传文件记录")
    public ServerResponseEntity<Void> save(@RequestBody List<AttachFileDTO> attachFileDtos) {
        List<AttachFile> attachFiles = mapperFacade.mapAsList(attachFileDtos, AttachFile.class);
        attachFileService.saveAttachFile(attachFiles);
        return ServerResponseEntity.success();
    }

    /**
     * 更改文件名或分组
     */
    @PutMapping("/update_file")
    @Operation(summary = "更新文件记录", description = "更新文件记录")
    public ServerResponseEntity<Void> updateFileName(@RequestBody AttachFileDTO attachFileDto) {
        if (Objects.isNull(attachFileDto.getFileName())) {
            // 图片名称不能为空
            throw new BusinessException(SupportBusinessError.SUPPORT_00001);
        }
        AttachFile attachFile = mapperFacade.map(attachFileDto, AttachFile.class);
        attachFileService.updateFileName(attachFile);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除上传文件记录", description = "根据上传文件记录表id删除上传文件记录")
    public ServerResponseEntity<Void> delete(@RequestParam Long fileId) {
        attachFileService.deleteById(fileId);
        return ServerResponseEntity.success();
    }

}
