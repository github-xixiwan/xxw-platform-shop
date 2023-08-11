package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class UserAddrDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long addrId;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "是否默认地址 1是")
    private Integer isDefault;

    @Schema(description = "收货人")
    @Length(min = 2, max = 20, message = "收货人姓名需要在2到20个字符之间")
    private String consignee;

    @Schema(description = "省ID")
    private Long provinceId;

    @Schema(description = "省")
    private String province;

    @Schema(description = "城市ID")
    private Long cityId;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "区ID")
    private Long areaId;

    @Schema(description = "区")
    private String area;

    @Schema(description = "邮编")
    private String postCode;

    @Schema(description = "地址")
    private String addr;

    @Schema(description = "经度")
    private Double lng;

    @Schema(description = "纬度")
    private Double lat;
}
