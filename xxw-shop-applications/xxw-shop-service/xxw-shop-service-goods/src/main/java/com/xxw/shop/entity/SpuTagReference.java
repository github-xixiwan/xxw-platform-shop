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
@Table(value = "spu_tag_reference")
public class SpuTagReference implements Serializable {

    /**
     * 分组引用id
     */
    @Id(keyType = KeyType.Auto)
    private Long referenceId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 状态(1:正常,-1:删除)
     */
    private Boolean status;

    /**
     * 排序
     */
    private Integer seq;

}
