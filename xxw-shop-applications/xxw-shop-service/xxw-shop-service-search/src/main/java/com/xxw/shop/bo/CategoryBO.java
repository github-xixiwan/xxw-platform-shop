package com.xxw.shop.bo;

import com.xxw.shop.starter.canal.annotation.CanalModel;
import com.xxw.shop.starter.canal.common.FieldNamingPolicy;
import lombok.Data;

/**
 * 分类信息
 */
@Data
@CanalModel(database = "xxw-shop", table = "category", fieldNamingPolicy = FieldNamingPolicy.LOWER_UNDERSCORE)
public class CategoryBO {

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String desc;

    /**
     * 分类地址{parent_id}-{child_id},...
     */
    private String path;

    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    private Integer status;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 分类的显示图片
     */
    private String imgUrl;

    /**
     * 分类层级 从0开始
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer seq;
}
