package com.xxw.shop.feign;

import com.xxw.shop.api.business.feign.ShopDetailFeignClient;
import com.xxw.shop.api.business.vo.EsShopDetailVO;
import com.xxw.shop.api.business.vo.ShopDetailVO;
import com.xxw.shop.entity.ShopDetail;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.ShopDetailService;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class ShopDetailFeignController implements ShopDetailFeignClient {

    @Resource
    private ShopDetailService shopDetailService;

    @Resource
    private MapperFacade mapperFacade;

    @Override
    public ServerResponseEntity<String> getShopNameByShopId(Long shopId) {
        ShopDetailVO shopDetail = shopDetailService.getByShopId(shopId);
        if (Objects.isNull(shopDetail)) {
            return ServerResponseEntity.success("");
        }
        return ServerResponseEntity.success(shopDetail.getShopName());
    }

    @Override
    public ServerResponseEntity<EsShopDetailVO> getShopByShopId(Long shopId) {
        ShopDetailVO shopDetail = shopDetailService.getByShopId(shopId);
        if (Objects.isNull(shopDetail)) {
            return ServerResponseEntity.success(new EsShopDetailVO());
        }
        return ServerResponseEntity.success(mapperFacade.map(shopDetail, EsShopDetailVO.class));
    }


    @Override
    public ServerResponseEntity<List<ShopDetailVO>> listByShopIds(List<Long> shopIds) {
        List<ShopDetail> shopDetail = shopDetailService.listByShopIds(shopIds);
        return ServerResponseEntity.success(mapperFacade.mapAsList(shopDetail, ShopDetailVO.class));
    }

    @Override
    public ServerResponseEntity<EsShopDetailVO> shopExtensionData(Long shopId) {
        return ServerResponseEntity.success(shopDetailService.shopExtensionData(shopId));
    }

    @Override
    public ServerResponseEntity<List<ShopDetailVO>> getShopDetailByShopIdAndShopName(List<Long> shopIds,
                                                                                     String shopName) {
        return ServerResponseEntity.success(shopDetailService.getShopDetailByShopIdAndShopName(shopIds, shopName));
    }
}
