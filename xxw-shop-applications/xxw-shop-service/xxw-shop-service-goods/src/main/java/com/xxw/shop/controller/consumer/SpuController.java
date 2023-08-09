package com.xxw.shop.controller.consumer;

import com.xxw.shop.api.goods.vo.SpuVO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.SkuService;
import com.xxw.shop.service.SpuExtensionService;
import com.xxw.shop.service.SpuService;
import com.xxw.shop.vo.SkuConsumerVO;
import com.xxw.shop.vo.SpuConsumerVO;
import com.xxw.shop.vo.SpuExtensionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * spu信息
 */
@RestController("consumerSpuController")
@RequestMapping("/ua/spu")
@Tag(name = "app-spu信息")
public class SpuController {

    @Resource
    private SpuService spuService;

    @Resource
    private SpuExtensionService spuExtensionService;

    @Resource
    private SkuService skuService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/prod_info")
    @Operation(summary = "商品详情信息", description = "根据商品ID（prodId）获取商品信息")
    @Parameter(name = "spuId", description = "商品ID", required = true)
    public ServerResponseEntity<SpuConsumerVO> prodInfo(@RequestParam("spuId") Long spuId) {
        SpuVO spu = spuService.getBySpuId(spuId);
        SpuConsumerVO spuConsumerVO = mapperFacade.map(spu, SpuConsumerVO.class);
        SpuExtensionVO spuExtension = spuExtensionService.getBySpuId(spuId);
        spuConsumerVO.setTotalStock(spuExtension.getActualStock());
        spuConsumerVO.setSaleNum(spuExtension.getSaleNum());
        List<SkuConsumerVO> skuAppVO = skuService.getSkuBySpuId(spu.getSpuId());
        spuConsumerVO.setSkus(skuAppVO);
        return ServerResponseEntity.success(spuConsumerVO);
    }
}
