package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.ShopUser;
import org.apache.ibatis.annotations.Param;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface ShopUserMapper extends BaseMapper<ShopUser> {

    /**
     * 获取店主账号的用户id-第一个创建的账号（仅用于审核店铺）
     *
     * @param shopId
     * @return
     */
    Long getUserIdByShopId(@Param("shopId") Long shopId);
}
