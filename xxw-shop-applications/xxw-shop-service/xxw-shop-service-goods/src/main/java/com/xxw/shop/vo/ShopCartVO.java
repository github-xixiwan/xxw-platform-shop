package com.xxw.shop.vo;

import com.xxw.shop.api.goods.vo.ShopCartItemVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShopCartVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺ID", required = true)
    private Long shopId;

    @Schema(description = "店铺名称", required = true)
    private String shopName;

    @Schema(description = "店铺类型1自营店 2普通店")
    private Integer shopType;

    @Schema(description = "购物车商品信息")
    private List<ShopCartItemVO> shopCartItem;

    @Schema(description = "商品总值", required = true)
    private Long total;
    @Schema(description = "数量", required = true)
    private Integer totalCount;
}
