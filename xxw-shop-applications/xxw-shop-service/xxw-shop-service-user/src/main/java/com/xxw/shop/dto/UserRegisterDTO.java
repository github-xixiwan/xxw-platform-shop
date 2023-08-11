package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "用户注册信息")
public class UserRegisterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "密码")
    private String password;

    @Schema(description = "头像")
    private String img;

    @Schema(description = "昵称")
    private String nickName;

    @NotBlank
    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "当账户未绑定时，临时的uid")
    private String tempUid;

    @Schema(description = "用户id")
    private Long userId;
}
