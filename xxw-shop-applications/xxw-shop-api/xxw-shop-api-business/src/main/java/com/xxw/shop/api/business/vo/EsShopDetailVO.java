package com.xxw.shop.api.business.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class EsShopDetailVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "店铺类型1自营店 2普通店")
    private Integer type;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "店铺logo")
    private String shopLogo;

    @Schema(description = "店铺状态(-1:未开通 0: 停业中 1:营业中)")
    private Integer shopStatus;
}
