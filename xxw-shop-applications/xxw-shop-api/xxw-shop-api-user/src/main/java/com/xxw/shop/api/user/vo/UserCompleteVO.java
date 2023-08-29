package com.xxw.shop.api.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xxw.shop.api.support.serializer.ImgJsonSerializer;
import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserCompleteVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long userId;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "头像图片路径")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

    @Schema(description = "状态 1 正常 0 无效")
    private Integer status;

    @Schema(description = "是否创建过店铺")

    /**
     * openId list
     */
    private List<String> bizUserIdList;
}
