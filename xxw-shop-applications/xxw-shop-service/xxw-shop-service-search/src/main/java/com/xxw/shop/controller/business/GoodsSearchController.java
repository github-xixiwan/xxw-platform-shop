package com.xxw.shop.controller.business;

import com.xxw.shop.api.search.dto.GoodsSearchDTO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.api.search.vo.EsSpuVO;
import com.xxw.shop.constant.SearchTypeEnum;
import com.xxw.shop.manager.GoodsSearchManager;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("businessGoodsSearchController")
@RequestMapping("/b/search")
@Tag(name = "business-spu管理列表接口")
public class GoodsSearchController {

    @Resource
    private GoodsSearchManager goodsSearchManager;

    @GetMapping("/page")
    @Operation(summary = "商品信息列表", description = "商品信息列表")
    public ServerResponseEntity<EsPageVO<EsSpuVO>> page(@Valid GoodsSearchDTO dto) {
        Long shopId = AuthUserContext.get().getTenantId();
        dto.setSearchType(SearchTypeEnum.BUSINESS.value());
        dto.setShopId(shopId);
        EsPageVO<EsSpuVO> searchPage = goodsSearchManager.adminPage(dto);
        return ServerResponseEntity.success(searchPage);
    }

}
