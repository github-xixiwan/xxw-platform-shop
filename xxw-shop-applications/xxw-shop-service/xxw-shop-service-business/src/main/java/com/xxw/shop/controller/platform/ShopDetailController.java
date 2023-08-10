package com.xxw.shop.controller.platform;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.api.business.vo.ShopDetailVO;
import com.xxw.shop.dto.ShopDetailDTO;
import com.xxw.shop.dto.ShopDetailQueryDTO;
import com.xxw.shop.entity.ShopDetail;
import com.xxw.shop.module.common.constant.Constant;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.ShopDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 店铺详情
 */
@RestController("platformShopDetailController")
@RequestMapping("/platform/shop_detail")
@Tag(name = "platform-店铺信息")
public class ShopDetailController {

    @Resource
    private ShopDetailService shopDetailService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/page")
    @Operation(summary = "分页查询", description = "分页查询")
    public ServerResponseEntity<Page<ShopDetailVO>> page(ShopDetailQueryDTO dto) {
        if (!Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId())) {
            throw new BusinessException(SystemErrorEnumError.UNAUTHORIZED);
        }
        return ServerResponseEntity.success(shopDetailService.page(dto));
    }

    @GetMapping("/info")
    @Operation(summary = "店铺详情", description = "店铺详情")
    public ServerResponseEntity<ShopDetailVO> getInfo(@RequestParam Long shopId) {
        ShopDetailVO shopDetailVO = shopDetailService.getByShopId(shopId);
        return ServerResponseEntity.success(shopDetailVO);
    }

    /**
     * 新建店铺
     */
    @PostMapping("/create_shop")
    @Operation(summary = "新建店铺", description = "新建店铺")
    public ServerResponseEntity<Void> createShop(@RequestBody ShopDetailDTO dto) {
        shopDetailService.createShop(dto);
        return ServerResponseEntity.success();
    }

    @PutMapping("/update_shop")
    @Operation(summary = "更新店铺", description = "更新店铺")
    public ServerResponseEntity<Void> updateShop(@RequestBody ShopDetailDTO dto) {
        shopDetailService.updateShopDetail(mapperFacade.map(dto, ShopDetail.class));
        return ServerResponseEntity.success();
    }
}
