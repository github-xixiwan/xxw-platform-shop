package com.xxw.shop.module.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShopCartVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺类型1自营店 2普通店
     */
    private Integer shopType;

    /**
     * 购物车商品信息
     */
    private List<ShopCartItemVO> shopCartItem;

    /**
     * 商品总值
     */
    private Long total;

    /**
     * 数量
     */
    private Integer totalCount;
}
