package com.xxw.shop.dto;

import com.xxw.shop.api.goods.dto.ShopCartItemDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "立即购买时提交的商品项,如果该值为空，则说明是从购物车进入，如果该值不为空则说明为立即购买")
    private ShopCartItemDTO shopCartItem;

    @NotNull(message = "配送类型不能为空")
    @Schema(description = "配送类型3：无需快递")
    private Integer dvyType;

    @Schema(description = "地址ID，0为默认地址")
    @NotNull(message = "地址不能为空")
    private Long addrId;
}
