package com.xxw.shop.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xxw.shop.api.support.serializer.ImgJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSimpleInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户头像")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;
}
