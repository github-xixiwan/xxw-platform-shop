package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShopUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺用户id")
    private Long shopUserId;

    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "员工编号")
    private String code;

    @Schema(description = "联系方式")
    private String phoneNum;

    @Schema(description = "角色id列表")
    private List<Long> roleIds;
}
