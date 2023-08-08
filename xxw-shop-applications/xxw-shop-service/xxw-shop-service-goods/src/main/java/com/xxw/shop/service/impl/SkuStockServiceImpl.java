package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.dto.SkuDTO;
import com.xxw.shop.entity.SkuStock;
import com.xxw.shop.entity.table.SkuStockTableDef;
import com.xxw.shop.mapper.SkuStockMapper;
import com.xxw.shop.service.SkuStockService;
import com.xxw.shop.vo.SkuStockVO;
import com.xxw.shop.vo.SkuVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
@Service
public class SkuStockServiceImpl extends ServiceImpl<SkuStockMapper, SkuStock> implements SkuStockService {

    @Override
    public List<SkuStockVO> listBySkuList(List<SkuVO> skuVOList) {
        List<Long> skuIdList = skuVOList.stream().map(SkuVO::getSkuId).collect(Collectors.toList());
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SkuStockTableDef.SKU_STOCK.SKU_ID.in(skuIdList));
        return this.listAs(queryWrapper, SkuStockVO.class);
    }

    @Override
    public void updateBatch(List<SkuDTO> skuList) {
        if (CollUtil.isEmpty(skuList)) {
            return;
        }
        // 如果是修改库存，此时不需要改变锁定库存
        List<SkuStock> skuStocks = new ArrayList<>();
        for (SkuDTO sku : skuList) {
            SkuStock skuStock = new SkuStock();
            if (Objects.nonNull(sku.getChangeStock()) && sku.getChangeStock() > 0) {
                skuStock.setStock(sku.getChangeStock());
                skuStock.setSkuId(sku.getSkuId());
                skuStocks.add(skuStock);
            }
        }
        if (CollUtil.isNotEmpty(skuStocks)) {
            mapper.updateStock(skuStocks);
        }
    }
}
