package com.xxw.shop.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.api.goods.feign.SpuFeignClient;
import com.xxw.shop.api.goods.vo.SpuVO;
import com.xxw.shop.dto.IndexImgDTO;
import com.xxw.shop.dto.IndexImgQueryDTO;
import com.xxw.shop.entity.IndexImg;
import com.xxw.shop.module.common.constant.StatusEnum;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.IndexImgService;
import com.xxw.shop.vo.IndexImgVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 轮播图
 */
@RestController("adminIndexImgController")
@RequestMapping("/admin/index_img")
@Tag(name = "admin-轮播图")
public class IndexImgController {

    @Resource
    private IndexImgService indexImgService;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private SpuFeignClient spuFeignClient;

    @GetMapping("/page")
    @Operation(summary = "获取轮播图列表", description = "分页获取轮播图列表")
    public ServerResponseEntity<Page<IndexImgVO>> page(@Valid IndexImgQueryDTO dto) {
        dto.setShopId(AuthUserContext.get().getTenantId());
        return ServerResponseEntity.success(indexImgService.page(dto));
    }

    @GetMapping
    @Operation(summary = "获取轮播图", description = "根据imgId获取轮播图")
    public ServerResponseEntity<IndexImgVO> getByImgId(@RequestParam Long imgId) {
        IndexImgVO indexImg = indexImgService.getByImgId(imgId);
        if (Objects.nonNull(indexImg.getSpuId())) {
            ServerResponseEntity<SpuVO> spuResponse = spuFeignClient.getById(indexImg.getSpuId());
            indexImg.setSpu(spuResponse.getData());
        }
        return ServerResponseEntity.success(indexImg);
    }

    @PostMapping
    @Operation(summary = "保存轮播图", description = "保存轮播图")
    public ServerResponseEntity<Void> save(@Valid @RequestBody IndexImgDTO indexImgDTO) {
        IndexImg indexImg = mapperFacade.map(indexImgDTO, IndexImg.class);
        indexImg.setImgId(null);
        indexImg.setShopId(AuthUserContext.get().getTenantId());
        indexImg.setStatus(StatusEnum.ENABLE.value());
        indexImgService.save(indexImg);
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新轮播图", description = "更新轮播图")
    public ServerResponseEntity<Void> update(@Valid @RequestBody IndexImgDTO indexImgDTO) {
        IndexImg indexImg = mapperFacade.map(indexImgDTO, IndexImg.class);
        indexImg.setShopId(AuthUserContext.get().getTenantId());
        indexImgService.updateIndexImg(indexImg);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除轮播图", description = "根据轮播图id删除轮播图")
    public ServerResponseEntity<Void> delete(@RequestParam Long imgId) {
        indexImgService.deleteById(imgId, AuthUserContext.get().getTenantId());
        return ServerResponseEntity.success();
    }
}
