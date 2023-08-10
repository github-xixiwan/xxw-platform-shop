package com.xxw.shop.controller.consumer;

import com.xxw.shop.api.business.vo.ShopDetailVO;
import com.xxw.shop.constant.BusinessBusinessError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.ShopDetailService;
import com.xxw.shop.vo.ShopHeadInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping(value = "/ua/shop_detail")
@RestController("consumerShopDetailController")
@Tag(name = "consumer-店铺详情信息")
public class ShopDetailController {

    @Resource
    private ShopDetailService shopDetailService;

    @GetMapping("/check_shop_name")
    @Operation(summary = "验证店铺名称是否重名", description = "验证店铺名称是否重名")
    public ServerResponseEntity<Boolean> checkShopName(@RequestParam("shopName") String shopName) {
        Boolean res = shopDetailService.checkShopName(shopName);
        return ServerResponseEntity.success(res);
    }

    @GetMapping("/head_info")
    @Operation(summary = "店铺头部信息", description = "店铺头部信息")
    public ServerResponseEntity<ShopHeadInfoVO> getShopHeadInfo(Long shopId) {
        ShopHeadInfoVO shopHeadInfoVO = new ShopHeadInfoVO();
        ShopDetailVO shopDetailVO = shopDetailService.getByShopId(shopId);
        if (Objects.isNull(shopDetailVO)) {
            throw new BusinessException(BusinessBusinessError.BUSINESS_00006);
        }
        shopHeadInfoVO.setShopStatus(shopDetailVO.getShopStatus());
        if (!Objects.equals(shopDetailVO.getShopStatus(), 1)) {
            return ServerResponseEntity.success(shopHeadInfoVO);
        }
        shopHeadInfoVO.setShopId(shopId);
        shopHeadInfoVO.setType(shopDetailVO.getType());
        shopHeadInfoVO.setIntro(shopDetailVO.getIntro());
        shopHeadInfoVO.setShopLogo(shopDetailVO.getShopLogo());
        shopHeadInfoVO.setShopName(shopDetailVO.getShopName());
        shopHeadInfoVO.setMobileBackgroundPic(shopDetailVO.getMobileBackgroundPic());
        return ServerResponseEntity.success(shopHeadInfoVO);
    }
}
