package com.xxw.shop.api.user.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AreaVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema()
    private Long areaId;

    @Schema(description = "地址")
    private String areaName;

    @Schema(description = "上级地址")
    private Long parentId;

    @Schema(description = "等级（从1开始）")
    private Integer level;

    private Integer check;

    /**
     * 下级地址集合
     */
    private List<AreaVO> areas;

    /**
     * 下级地址的areaId
     */
    private List<Long> areaIds;
}
