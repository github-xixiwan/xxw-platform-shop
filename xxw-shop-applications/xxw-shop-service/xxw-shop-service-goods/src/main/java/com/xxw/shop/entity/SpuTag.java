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
@Table(value = "spu_tag")
public class SpuTag implements Serializable {

    /**
     * 分组标签id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 分组标题
     */
    private String title;

    /**
     * 店铺Id
     */
    private Long shopId;

    /**
     * 状态(1为正常,-1为删除)
     */
    private Boolean status;

    /**
     * 默认类型(0:商家自定义,1:系统默认)
     */
    private Boolean isDefault;

    /**
     * 商品数量
     */
    private Long prodCount;

    /**
     * 列表样式(0:一列一个,1:一列两个,2:一列三个)
     */
    private Integer style;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

}
