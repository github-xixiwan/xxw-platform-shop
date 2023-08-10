package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.ShopDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface ShopDetailMapper extends BaseMapper<ShopDetail> {

    /**
     * 统计该店铺名被其他用户使用的数量
     *
     * @param shopName
     * @param shopId
     * @return
     */
    int countShopName(@Param("shopName") String shopName, @Param("shopId") Long shopId);
}
