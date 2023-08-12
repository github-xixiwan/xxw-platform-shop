package com.xxw.shop.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.cache.BusinessCacheNames;
import com.xxw.shop.dto.HotSearchQueryDTO;
import com.xxw.shop.entity.HotSearch;
import com.xxw.shop.mapper.HotSearchMapper;
import com.xxw.shop.service.HotSearchService;
import com.xxw.shop.vo.HotSearchVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.xxw.shop.entity.table.HotSearchTableDef.HOT_SEARCH;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
@Service
public class HotSearchServiceImpl extends ServiceImpl<HotSearchMapper, HotSearch> implements HotSearchService {

    @Override
    public Page<HotSearchVO> page(HotSearchQueryDTO dto) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(HOT_SEARCH.SHOP_ID.eq(dto.getShopId()));
        queryWrapper.and(HOT_SEARCH.CONTENT.like(dto.getContent()));
        queryWrapper.and(HOT_SEARCH.STATUS.eq(dto.getStatus()));
        queryWrapper.and(HOT_SEARCH.TITLE.like(dto.getTitle()));
        queryWrapper.orderBy(HOT_SEARCH.SEQ.asc());
        return this.pageAs(new Page<>(dto.getPageNumber(), dto.getPageSize()), queryWrapper, HotSearchVO.class);
    }

    @Override
    public HotSearchVO getByHotSearchId(Long hotSearchId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(HOT_SEARCH.HOT_SEARCH_ID.eq(hotSearchId));
        return this.getOneAs(queryWrapper, HotSearchVO.class);
    }

    @Override
    public void deleteById(Long hotSearchId, Long shopId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(HOT_SEARCH.HOT_SEARCH_ID.eq(hotSearchId));
        queryWrapper.and(HOT_SEARCH.SHOP_ID.eq(shopId));
        this.remove(queryWrapper);
    }

    @Override
    @Cacheable(cacheNames = BusinessCacheNames.HOT_SEARCH_LIST_KEY, key = "#shopId")
    public List<HotSearchVO> listByShopId(Long shopId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(HOT_SEARCH.SHOP_ID.eq(shopId));
        return this.listAs(queryWrapper, HotSearchVO.class);
    }

    @Override
    @CacheEvict(cacheNames = BusinessCacheNames.HOT_SEARCH_LIST_KEY, key = "#shopId")
    public void removeHotSearchListCache(Long shopId) {
    }
}
