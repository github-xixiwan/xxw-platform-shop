package com.xxw.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePasswordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "oldPassword NotBlank")
    @Schema(description = "旧密码", required = true)
    private String oldPassword;

    @NotNull(message = "newPassword NotNull")
    @Schema(description = "新密码", required = true)
    private String newPassword;
}
