package com.xxw.shop.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class HotSearchVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long hotSearchId;

    @Schema(description = "店铺ID 0为全局热搜")
    private Long shopId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "顺序")
    private Integer seq;

    @Schema(description = "状态 0下线 1上线")
    private Integer status;

    @Schema(description = "热搜标题")
    private String title;
}
