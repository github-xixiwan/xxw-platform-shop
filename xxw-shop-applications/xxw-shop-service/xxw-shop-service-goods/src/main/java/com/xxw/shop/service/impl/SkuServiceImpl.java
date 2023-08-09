package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.cache.GoodsCacheNames;
import com.xxw.shop.dto.SkuDTO;
import com.xxw.shop.dto.SpuDTO;
import com.xxw.shop.entity.Sku;
import com.xxw.shop.entity.SkuStock;
import com.xxw.shop.entity.SpuSkuAttrValue;
import com.xxw.shop.mapper.SkuMapper;
import com.xxw.shop.module.cache.tool.IGlobalRedisCache;
import com.xxw.shop.module.common.cache.CacheNames;
import com.xxw.shop.module.common.constant.StatusEnum;
import com.xxw.shop.module.web.util.SpringContextUtils;
import com.xxw.shop.service.SkuService;
import com.xxw.shop.service.SkuStockService;
import com.xxw.shop.service.SpuSkuAttrValueService;
import com.xxw.shop.vo.SkuConsumerVO;
import com.xxw.shop.api.goods.vo.SkuVO;
import com.xxw.shop.api.goods.vo.SpuSkuAttrValueVO;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xxw.shop.entity.table.SkuTableDef.SKU;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

    @Resource
    private IGlobalRedisCache globalRedisCache;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private SkuStockService skuStockService;

    @Resource
    private SpuSkuAttrValueService spuSkuAttrValueService;

    @Override
    public void saveSku(Long spuId, List<SkuDTO> skuList) {
        List<Sku> list = skuList.stream().map(l -> {
            Sku sku = mapperFacade.map(l, Sku.class);
            sku.setSpuId(spuId);
            sku.setStatus(StatusEnum.ENABLE.value());
            return sku;
        }).collect(Collectors.toList());
        skuList.forEach(sku -> {
            sku.setSpuId(spuId);
            sku.setStatus(StatusEnum.ENABLE.value());
        });
        // 处理数据，保存库存、属性
        this.saveBatch(list);
        List<SkuStock> skuStocks = new ArrayList<>();
        List<SpuSkuAttrValue> spuSkuAttrValues = new ArrayList<>();
        for (SkuDTO skuDTO : skuList) {
            SkuStock skuStock = new SkuStock();
            skuStock.setSkuId(skuDTO.getSkuId());
            skuStock.setStock(skuDTO.getStock());
            skuStock.setActualStock(skuDTO.getStock());
            skuStock.setLockStock(0);
            skuStocks.add(skuStock);
            List<SpuSkuAttrValue> spuSkuAttrValueList = mapperFacade.mapAsList(skuDTO.getSpuSkuAttrValues(),
                    SpuSkuAttrValue.class);
            for (SpuSkuAttrValue spuSkuAttrValue : spuSkuAttrValueList) {
                spuSkuAttrValue.setSpuId(spuId);
                spuSkuAttrValue.setSkuId(skuDTO.getSkuId());
                spuSkuAttrValue.setStatus(StatusEnum.ENABLE.value());
                spuSkuAttrValues.add(spuSkuAttrValue);
            }
        }
        skuStockService.saveBatch(skuStocks);
        spuSkuAttrValueService.saveBatch(spuSkuAttrValues);
    }


    @Override
    public void updateSku(Long spuId, List<SkuDTO> skuList) {
        // 获取当前商品所有的sku
        List<SkuVO> skuListDb = mapper.listBySpuId(spuId);
        List<Long> skuIdsDb = skuListDb.stream().map(SkuVO::getSkuId).collect(Collectors.toList());
        List<Long> skuIds = new ArrayList<>();
        List<SkuDTO> updateSkus = new ArrayList<>();
        List<SkuDTO> insertSkus = new ArrayList<>();
        for (SkuDTO sku : skuList) {
            sku.setSpuId(spuId);
            if (skuIdsDb.contains(sku.getSkuId())) {
                updateSkus.add(sku);
                skuIds.add(sku.getSkuId());
            } else if (Objects.isNull(sku.getSkuId())) {
                insertSkus.add(sku);
            }
        }
        // 新增的sku--保存
        if (CollUtil.isNotEmpty(insertSkus)) {
            saveSku(spuId, insertSkus);
        }
        // 已有的sku--更新
        if (CollUtil.isNotEmpty(updateSkus)) {
            List<Sku> skus = mapperFacade.mapAsList(updateSkus, Sku.class);
            this.updateBatch(skus);
            skuStockService.updateBatch(updateSkus);
        }
        // 不存在的sku--删除
        skuIdsDb.removeAll(skuIds);
        if (CollUtil.isNotEmpty(skuIdsDb)) {
            deleteSkuBatch(skuIdsDb);
        }
    }

    @Override
    @Cacheable(cacheNames = GoodsCacheNames.SKU_LIST_KEY, key = "#spuId", sync = true)
    public List<SkuVO> listBySpuId(Long spuId) {
        return mapper.listBySpuId(spuId);
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = GoodsCacheNames.SKU_LIST_KEY, key = "#spuId"), @CacheEvict(cacheNames
            = GoodsCacheNames.SKU_OF_SPU_DETAIL_KEY, key = "#spuId")})
    public void removeSkuCacheBySpuIdOrSkuIds(Long spuId, List<Long> skuIds) {
        // 根据spuId删除缓存
        if (CollUtil.isEmpty(skuIds)) {
            // 获取当前类的代理，这样就可以利用spring的方法获取缓存了，不要删了
            SkuServiceImpl skuService = (SkuServiceImpl) SpringContextUtils.getBean("skuServiceImpl");
            List<SkuVO> skuList = skuService.listBySpuId(spuId);
            skuIds = skuList.stream().map(SkuVO::getSkuId).collect(Collectors.toList());
        }
        List<String> keys = new ArrayList<>();
        for (Long skuId : skuIds) {
            keys.add(GoodsCacheNames.SKU_BY_ID_KEY + CacheNames.UNION + skuId);
        }
        globalRedisCache.del(keys.toArray(new String[0]));
    }

    @Override
    public void deleteBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setStatus(-1);
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SKU.SPU_ID.eq(spuId));
        this.update(sku, queryWrapper);
    }

    @Override
    public List<SkuVO> listBySpuIdAndExtendInfo(Long spuId) {
        return mapper.listBySpuIdAndExtendInfo(spuId);
    }

    @Override
    @Cacheable(cacheNames = GoodsCacheNames.SKU_BY_ID_KEY, key = "#skuId")
    public SkuVO getSkuBySkuId(Long skuId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SKU.SKU_ID.eq(skuId));
        return this.getOneAs(queryWrapper, SkuVO.class);
    }

    @Override
    public void updateAmountOrStock(SpuDTO spuDTO) {
        List<SkuDTO> skuList = spuDTO.getSkuList();
        List<Sku> skus = new ArrayList<>();
        boolean isUpdateStock = false;
        for (SkuDTO skuDTO : skuList) {
            if (Objects.nonNull(skuDTO.getChangeStock()) && skuDTO.getChangeStock() > 0) {
                isUpdateStock = true;
                break;
            } else if (Objects.nonNull(skuDTO.getPriceFee())) {
                Sku sku = new Sku();
                sku.setSkuId(skuDTO.getSkuId());
                sku.setPriceFee(skuDTO.getPriceFee());
                skus.add(sku);
            }
        }
        if (isUpdateStock) {
            skuStockService.updateBatch(skuList);
        }
        if (CollUtil.isNotEmpty(skus)) {
            this.updateBatch(skus);
        }
    }

    @Override
    @Cacheable(cacheNames = GoodsCacheNames.SKU_OF_SPU_DETAIL_KEY, key = "#spuId", sync = true)
    public List<SkuConsumerVO> getSkuBySpuId(Long spuId) {
        String attrUnionAttrValue = ":";
        String attrUnionAttr = ";";
        List<SkuConsumerVO> skuAppList = new ArrayList<>();
        List<SkuVO> skuData = mapper.getSkuBySpuId(spuId);
        for (SkuVO sku : skuData) {
            SkuConsumerVO skuAppVO = mapperFacade.map(sku, SkuConsumerVO.class);
            StringBuilder properties = new StringBuilder();
            for (SpuSkuAttrValueVO spuSkuAttrValue : sku.getSpuSkuAttrValues()) {
                properties.append(spuSkuAttrValue.getAttrName()).append(attrUnionAttrValue).append(spuSkuAttrValue.getAttrValueName()).append(attrUnionAttr);
            }
            skuAppVO.setProperties(properties.substring(0, properties.length() - 1));
            skuAppList.add(skuAppVO);
        }
        return skuAppList;
    }

    private void deleteSkuBatch(List<Long> skuIds) {
        List<Sku> skus = new ArrayList<>();
        for (Long skuId : skuIds) {
            Sku sku = new Sku();
            sku.setSkuId(skuId);
            sku.setStatus(StatusEnum.DELETE.value());
            skus.add(sku);
        }
        this.updateBatch(skus);
        spuSkuAttrValueService.changeStatusBySkuId(skuIds, StatusEnum.DELETE.value());
    }
}
