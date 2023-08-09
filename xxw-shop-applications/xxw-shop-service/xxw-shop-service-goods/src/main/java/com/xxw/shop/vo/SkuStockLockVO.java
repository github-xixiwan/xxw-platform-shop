package com.xxw.shop.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SkuStockLockVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long skuId;

    private Long spuId;

    private Integer count;
}
