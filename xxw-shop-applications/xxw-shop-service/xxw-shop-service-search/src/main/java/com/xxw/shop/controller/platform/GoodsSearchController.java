package com.xxw.shop.controller.platform;

import com.xxw.shop.api.search.dto.GoodsSearchDTO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.constant.SearchTypeEnum;
import com.xxw.shop.manager.GoodsSearchManager;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.vo.SpuAdminVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("platformGoodsSearchController")
@RequestMapping("/p/search")
@Tag(name = "platform-spu列表接口")
public class GoodsSearchController {

    @Resource
    private GoodsSearchManager goodsSearchManager;

    @GetMapping("/page")
    @Operation(summary = "商品管理信息列表（平台端）", description = "商品管理信息列表（平台端）")
    public ServerResponseEntity<EsPageVO<SpuAdminVO>> adminPage(@Valid GoodsSearchDTO dto) {
        dto.setSearchType(SearchTypeEnum.PLATFORM.value());
        EsPageVO<SpuAdminVO> searchPage = goodsSearchManager.adminPage(dto);
        return ServerResponseEntity.success(searchPage);
    }
}
