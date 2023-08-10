package com.xxw.shop.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.cache.BusinessCacheNames;
import com.xxw.shop.dto.IndexImgQueryDTO;
import com.xxw.shop.entity.IndexImg;
import com.xxw.shop.mapper.IndexImgMapper;
import com.xxw.shop.service.IndexImgService;
import com.xxw.shop.vo.IndexImgVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.xxw.shop.entity.table.IndexImgTableDef.INDEX_IMG;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
@Service
public class IndexImgServiceImpl extends ServiceImpl<IndexImgMapper, IndexImg> implements IndexImgService {

    @Override
    public Page<IndexImgVO> page(IndexImgQueryDTO dto) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(INDEX_IMG.IMG_TYPE.eq(dto.getImgType()));
        queryWrapper.and(INDEX_IMG.SHOP_ID.eq(dto.getShopId()));
        queryWrapper.and(INDEX_IMG.STATUS.eq(dto.getStatus()));
        queryWrapper.orderBy(INDEX_IMG.SEQ.desc(), INDEX_IMG.IMG_ID.desc());
        return this.pageAs(new Page<>(dto.getPageNumber(), dto.getPageSize()), queryWrapper, IndexImgVO.class);
    }

    @Override
    public IndexImgVO getByImgId(Long imgId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(INDEX_IMG.IMG_ID.eq(imgId));
        return this.getOneAs(queryWrapper, IndexImgVO.class);
    }

    @Override
    @CacheEvict(cacheNames = BusinessCacheNames.INDEX_IMG_KEY, key = "#indexImg.shopId")
    public void saveIndexImg(IndexImg indexImg) {
        this.save(indexImg);
    }

    @Override
    @CacheEvict(cacheNames = BusinessCacheNames.INDEX_IMG_KEY, key = "#indexImg.shopId")
    public void updateIndexImg(IndexImg indexImg) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(INDEX_IMG.IMG_ID.eq(indexImg.getImgId()));
        queryWrapper.and(INDEX_IMG.SHOP_ID.eq(indexImg.getShopId()));
        this.update(indexImg, queryWrapper);
    }

    @Override
    @CacheEvict(cacheNames = BusinessCacheNames.INDEX_IMG_KEY, key = "#shopId")
    public void deleteById(Long imgId, Long shopId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(INDEX_IMG.IMG_ID.eq(imgId));
        queryWrapper.and(INDEX_IMG.SHOP_ID.eq(shopId));
        this.remove(queryWrapper);
    }

    @Override
    @Cacheable(cacheNames = BusinessCacheNames.INDEX_IMG_KEY, key = "#shopId", sync = true)
    public List<IndexImgVO> getListByShopId(Long shopId) {
        return mapper.getListByShopId(shopId);
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = BusinessCacheNames.INDEX_IMG_KEY, key = "#shopId"),
            @CacheEvict(cacheNames = BusinessCacheNames.INDEX_IMG_KEY, key = "0")})
    public void deleteBySpuId(Long spuId, Long shopId) {
        mapper.clearSpuIdBySpuId(spuId);
    }
}
