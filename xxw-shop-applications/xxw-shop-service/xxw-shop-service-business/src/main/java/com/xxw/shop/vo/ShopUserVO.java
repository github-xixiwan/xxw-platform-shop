package com.xxw.shop.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mybatisflex.annotation.Column;
import com.xxw.shop.api.support.serializer.ImgJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShopUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * shopUserId
     */
    @Schema(description = "店铺用户id")
    private Long shopUserId;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 员工编号
     */
    @Schema(description = "员工编号")
    private String code;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    private String phoneNum;

    @Schema(description = "是否已经有账号了")
    private Integer hasAccount;

    @Schema(description = "店铺id")
    private Long shopId;

    @JsonSerialize(using = ImgJsonSerializer.class)
    private String avatar;

    private Integer isAdmin;

    @Column(ignore = true)
    @Schema(description = "角色id列表")
    private List<Long> roleIds;
}
