package com.xxw.shop.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
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
@Table(value = "sku_stock_lock")
public class SkuStockLock implements Serializable {

    /**
     * id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

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
     * sku id
     */
    private Long skuId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 状态-1已解锁 0待确定 1已锁定
     */
    private Integer status;

    /**
     * 锁定库存数量
     */
    private Integer count;

}
