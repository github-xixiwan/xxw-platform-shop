package com.xxw.shop.controller.minio;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.minio.vo.OssVO;
import com.xxw.shop.starter.minio.MinioComponent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/oss")
@RestController
@Tag(name = "对象存储")
public class OssController {

    /**
     * 上传的文件夹(根据时间确定)
     */
    public static final String NORM_DAY_PATTERN = "yyyy/MM/dd";

    @Resource
    private MinioComponent minioComponent;

    @GetMapping(value = "/info")
    @Operation(summary = "token", description = "获取文件上传需要的token")
    @Parameter(name = "fileNum", description = "需要获取token的文件数量")
    public ServerResponseEntity<OssVO> info(@RequestParam("fileNum") Integer fileNum) {
        OssVO ossVO = new OssVO();
        fillMinIoInfo(ossVO, fileNum);
        return ServerResponseEntity.success(ossVO);
    }

    private void fillMinIoInfo(OssVO ossVO, Integer fileNum) {
        List<OssVO> fileVOList = new ArrayList<>();
        for (int i = 0; i < fileNum; i++) {
            OssVO vo = loadFileVO(new OssVO());
            String actionUrl = minioComponent.getPresignedObjectUrl(vo.getDir() + vo.getFileName());
            vo.setActionUrl(actionUrl);
            fileVOList.add(vo);
        }
        ossVO.setOssList(fileVOList);
    }

    private OssVO loadFileVO(OssVO fileVo) {
        String dir = DateUtil.format(new Date(), NORM_DAY_PATTERN) + "/";
        String fileName = IdUtil.simpleUUID();
        fileVo.setDir(dir);
        fileVo.setFileName(fileName);
        return fileVo;
    }


    @PostMapping("/upload_minio")
    @Operation(summary = "文件上传接口", description = "上传文件，返回文件路径与域名")
    public ServerResponseEntity<OssVO> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ServerResponseEntity.success();
        }
        OssVO vo = loadFileVO(new OssVO());
        minioComponent.upload(file, vo.getDir() + vo.getFileName());
        return ServerResponseEntity.success(vo);
    }

}
