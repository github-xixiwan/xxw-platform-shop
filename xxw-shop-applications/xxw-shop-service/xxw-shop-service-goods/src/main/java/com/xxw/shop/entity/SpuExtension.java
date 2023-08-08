package com.xxw.shop.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "spu_extension")
public class SpuExtension implements Serializable {

    /**
     * 商品扩展信息表id
     */
    @Id(keyType = KeyType.Auto)
    private Long spuExtendId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 销量
     */
    private Long saleNum;

    /**
     * 实际库存
     */
    private Long actualStock;

    /**
     * 锁定库存
     */
    private Long lockStock;

    /**
     * 可售卖库存
     */
    private Long stock;

}
