package com.xxw.shop.bo;

import com.xxw.shop.starter.canal.annotation.CanalModel;
import com.xxw.shop.starter.canal.common.FieldNamingPolicy;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品拓展信息
 */
@Data
@CanalModel(database = "mall4cloud_product", table = "spu_extension", fieldNamingPolicy =
        FieldNamingPolicy.LOWER_UNDERSCORE)
public class SpuExtensionBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 商品扩展信息表id
     */
    private Long spuExtendId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 销量
     */
    private Integer saleNum;

    /**
     * 实际库存
     */
    private Integer actualStock;

    /**
     * 锁定库存
     */
    private Integer lockStock;

    /**
     * 可售卖库存
     */
    private Integer stock;
}
