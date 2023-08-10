package com.xxw.shop.api.search.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShopInfoSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺名称 搜索华为的时候，可以把华为的旗舰店搜索出来")
    private String shopName;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "店铺logo")
    private String shopLogo;

    @Schema(description = "店铺类型1自营店 2普通店")
    private Integer type;
}
