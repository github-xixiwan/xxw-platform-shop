package com.xxw.shop.bo;

import com.xxw.shop.starter.canal.annotation.CanalModel;
import com.xxw.shop.starter.canal.common.FieldNamingPolicy;
import lombok.Data;

/**
 * 品牌信息
 */
@Data
@CanalModel(database = "xxw-shop", table = "shop_detail", fieldNamingPolicy = FieldNamingPolicy.LOWER_UNDERSCORE)
public class ShopDetailBO {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺类型1自营店 2普通店
     */
    private Integer type;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺logo(可修改)
     */
    private String shopLogo;

    /**
     * 店铺状态(-1:未开通 0: 停业中 1:营业中 2:平台下线 3:平台下线待审核)
     */
    private Integer shopStatus;
}
