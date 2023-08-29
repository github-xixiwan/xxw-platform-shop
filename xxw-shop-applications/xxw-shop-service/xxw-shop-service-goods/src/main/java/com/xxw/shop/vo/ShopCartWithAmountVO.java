package com.xxw.shop.vo;

import com.xxw.shop.api.goods.vo.ShopCartVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShopCartWithAmountVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "总额" )
    private Long totalMoney;

    @Schema(description = "总计" )
    private Long finalMoney;

    @Schema(description = "商品数量" )
    private Integer count;

    @Schema(description = "多个店铺的购物车信息" )
    private List<ShopCartVO> shopCarts;
}
