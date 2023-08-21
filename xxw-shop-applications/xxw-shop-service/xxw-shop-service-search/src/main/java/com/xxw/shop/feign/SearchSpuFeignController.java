package com.xxw.shop.feign;

import com.xxw.shop.api.search.dto.GoodsSearchDTO;
import com.xxw.shop.api.search.feign.SearchSpuFeignClient;
import com.xxw.shop.api.search.vo.EsGoodsSearchVO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.manager.GoodsSearchManager;
import com.xxw.shop.module.common.constant.Constant;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 商品搜索feign连接
 */
@RestController
public class SearchSpuFeignController implements SearchSpuFeignClient {

    @Resource
    private GoodsSearchManager goodsSearchManager;

    @Override
    public ServerResponseEntity<EsPageVO<EsGoodsSearchVO>> search(GoodsSearchDTO dto) {
        return ServerResponseEntity.success(goodsSearchManager.simplePage(dto));
    }

    @Override
    public ServerResponseEntity<EsPageVO<EsGoodsSearchVO>> spuPage(GoodsSearchDTO dto) {
        GoodsSearchDTO goodsSearchDTO = new GoodsSearchDTO();
        // 平台id则搜索整个平台的商品
        Long shopId = dto.getShopId();
        if (!Objects.equals(shopId, Constant.PLATFORM_SHOP_ID)) {
            goodsSearchDTO.setShopId(shopId);
        }
        EsPageVO<EsGoodsSearchVO> page = goodsSearchManager.page(dto);
        return ServerResponseEntity.success(page);
    }
}
