package com.xxw.shop.module.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xxw.shop.api.support.serializer.ImgJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserSimpleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @Schema(description = "头像")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String avatar;

    private Integer isAdmin;

}
