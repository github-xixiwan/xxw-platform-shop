package com.xxw.shop.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.HotSearchQueryDTO;
import com.xxw.shop.entity.HotSearch;
import com.xxw.shop.vo.HotSearchVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface HotSearchService extends IService<HotSearch> {

    Page<HotSearchVO> page(HotSearchQueryDTO dto);

    HotSearchVO getByHotSearchId(Long hotSearchId);

    void deleteById(Long hotSearchId, Long shopId);

    List<HotSearchVO> listByShopId(Long shopId);

    void removeHotSearchListCache(Long shopId);
}
