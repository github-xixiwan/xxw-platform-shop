package com.xxw.shop.vo;

import com.xxw.shop.module.web.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpuExtensionVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品扩展信息表id")
    private Long spuExtendId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "商品id")
    private Long spuId;

    @Schema(description = "销量")
    private Integer saleNum;

    @Schema(description = "实际库存")
    private Integer actualStock;

    @Schema(description = "锁定库存")
    private Integer lockStock;

    @Schema(description = "可售卖库存")
    private Integer stock;
}
