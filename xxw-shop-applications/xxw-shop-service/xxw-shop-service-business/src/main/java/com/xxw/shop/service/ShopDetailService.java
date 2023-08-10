package com.xxw.shop.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.api.business.vo.EsShopDetailVO;
import com.xxw.shop.api.business.vo.ShopDetailVO;
import com.xxw.shop.dto.ShopDetailDTO;
import com.xxw.shop.dto.ShopDetailQueryDTO;
import com.xxw.shop.entity.ShopDetail;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface ShopDetailService extends IService<ShopDetail> {
    Page<ShopDetailVO> page(ShopDetailQueryDTO dto);

    ShopDetailVO getByShopId(Long shopId);

    void updateShopDetail(ShopDetail shopDetail);

    void deleteById(Long shopId);

    List<ShopDetail> listByShopIds(List<Long> shopIds);

    void applyShop(ShopDetailDTO dto);

    void changeSpuStatus(Long shopId, Integer shopStatus);

    void removeCacheByShopId(Long shopId);

    EsShopDetailVO shopExtensionData(Long shopId);

    void createShop(ShopDetailDTO shopDetailDTO);

    List<ShopDetailVO> getShopDetailByShopIdAndShopName(List<Long> shopIds, String shopName);

    boolean checkShopName(String shopName);

}
