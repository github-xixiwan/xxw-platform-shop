package com.xxw.shop.api.business.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShopDetailVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "店铺类型1自营店 2普通店")
    private Integer type;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "店铺简介")
    private String intro;

    @Schema(description = "店铺logo(可修改)")
    private String shopLogo;

    @Schema(description = "店铺状态(-1:已删除 0: 停业中 1:营业中)")
    private Integer shopStatus;

    @Schema(description = "营业执照")
    private String businessLicense;

    @Schema(description = "身份证正面")
    private String identityCardFront;

    @Schema(description = "身份证反面")
    private String identityCardLater;

    @Schema(description = "移动端背景图")
    private String mobileBackgroundPic;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;
}
