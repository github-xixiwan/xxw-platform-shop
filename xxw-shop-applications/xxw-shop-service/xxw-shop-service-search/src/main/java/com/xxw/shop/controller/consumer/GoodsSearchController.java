package com.xxw.shop.controller.consumer;

import cn.hutool.core.collection.CollUtil;
import com.xxw.shop.api.business.feign.ShopDetailFeignClient;
import com.xxw.shop.api.business.vo.ShopDetailVO;
import com.xxw.shop.api.search.dto.GoodsSearchDTO;
import com.xxw.shop.api.search.vo.EsGoodsSearchVO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.api.search.vo.ShopInfoSearchVO;
import com.xxw.shop.manager.GoodsSearchManager;
import com.xxw.shop.module.common.constant.StatusEnum;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 商品搜索
 */
@RestController("consumerGoodsSearchController")
@RequestMapping("/ua/search")
@Tag(name = "consumer-spu搜索接口")
public class GoodsSearchController {

    @Resource
    private GoodsSearchManager goodsSearchManager;

    @Resource
    private ShopDetailFeignClient shopDetailFeignClient;

    @GetMapping("/page")
    @Operation(summary = "商品信息列表-包含spu、品牌、分类、属性和店铺信息", description = "spu列表-包含spu、品牌、分类、属性和店铺信息")
    public ServerResponseEntity<EsPageVO<EsGoodsSearchVO>> page(@Valid GoodsSearchDTO dto) {
        dto.setSpuStatus(StatusEnum.ENABLE.value());
        EsPageVO<EsGoodsSearchVO> searchPage = goodsSearchManager.page(dto);
        loadShopData(searchPage.getRecords());
        return ServerResponseEntity.success(searchPage);
    }

    @GetMapping("/simple_page")
    @Operation(summary = "商品信息列表-包含spu信息", description = "商品信息列表-包含spu信息")
    public ServerResponseEntity<EsPageVO<EsGoodsSearchVO>> simplePage(@Valid GoodsSearchDTO dto) {
        dto.setSpuStatus(StatusEnum.ENABLE.value());
        EsPageVO<EsGoodsSearchVO> searchPage = goodsSearchManager.simplePage(dto);
        loadShopData(searchPage.getRecords());
        return ServerResponseEntity.success(searchPage);
    }

    /**
     * 获取店铺数据
     *
     * @param list
     */
    private void loadShopData(List<EsGoodsSearchVO> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        for (EsGoodsSearchVO vo : list) {
            if (Objects.isNull(vo.getShopInfo()) || Objects.isNull(vo.getShopInfo().getShopId())) {
                continue;
            }
            ServerResponseEntity<ShopDetailVO> shopDataResponse =
                    shopDetailFeignClient.shopExtensionData(vo.getShopInfo().getShopId());
            if (Objects.equals(shopDataResponse.getCode(), SystemErrorEnumError.OK.getCode())) {
                ShopDetailVO shopDetailVO = shopDataResponse.getData();
                ShopInfoSearchVO shopInfo = vo.getShopInfo();
                shopInfo.setShopLogo(shopDetailVO.getShopLogo());
                shopInfo.setShopName(shopDetailVO.getShopName());
                shopInfo.setType(shopDetailVO.getType());
            }
        }
    }
}
