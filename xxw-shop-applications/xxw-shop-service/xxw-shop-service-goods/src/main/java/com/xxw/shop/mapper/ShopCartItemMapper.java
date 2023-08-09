package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.dto.CheckShopCartItemDTO;
import com.xxw.shop.entity.ShopCartItem;
import com.xxw.shop.api.goods.vo.ShopCartItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface ShopCartItemMapper extends BaseMapper<ShopCartItem> {

    /**
     * 根据用户id获取用户的购物车信息
     *
     * @param userId    用户id
     * @param isExpiry  是否已过期
     * @param isChecked 是否被选中
     * @return 购物车项
     */
    List<ShopCartItemVO> getShopCartItems(@Param("userId") Long userId, @Param("isExpiry") Boolean isExpiry, @Param(
            "isChecked") Boolean isChecked);

    /**
     * 购物项数量，有缓存
     *
     * @param userId 用户id
     * @return 购物项数量
     */
    Integer getShopCartItemCount(@Param("userId") Long userId);

    /**
     * 根据商品id，获取用户id，用于清空购物车商品数量的缓存
     *
     * @param spuId 商品id
     * @return 用户id列表
     */
    List<String> listUserIdBySpuId(@Param("spuId") Long spuId);

    /**
     * 勾选购车车状态
     *
     * @param userId             用户id
     * @param checkShopCartItems 参数
     */
    void checkShopCartItems(@Param("userId") Long userId,
                            @Param("checkShopCartItems") List<CheckShopCartItemDTO> checkShopCartItems);
}
