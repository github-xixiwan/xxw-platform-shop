package com.xxw.shop.listener;

import cn.hutool.core.util.StrUtil;
import com.xxw.shop.api.goods.constant.CategoryLevel;
import com.xxw.shop.api.goods.feign.CategoryFeignClient;
import com.xxw.shop.api.goods.feign.GoodsFeignClient;
import com.xxw.shop.bo.CategoryBO;
import com.xxw.shop.manager.GoodsUpdateManager;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import com.xxw.shop.module.common.constant.Constant;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.starter.canal.model.CanalBinLogResult;
import com.xxw.shop.starter.canal.support.processor.BaseCanalBinlogEventProcessor;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CategoryCanalListener extends BaseCanalBinlogEventProcessor<CategoryBO> {

    private static final Logger log = LoggerFactory.getLogger(CategoryCanalListener.class);

    @Resource
    private CategoryFeignClient categoryFeignClient;

    @Resource
    private GoodsUpdateManager goodsUpdateManager;

    @Resource
    private GoodsFeignClient goodsFeignClient;

    /**
     * 插入商品，此时插入es
     */
    @Override
    protected void processInsertInternal(CanalBinLogResult<CategoryBO> categoryBo) {

    }

    /**
     * 更新分类，删除商品索引，再重新构建一个
     *
     * @param result
     */
    @Override
    protected void processUpdateInternal(CanalBinLogResult<CategoryBO> result) {
        CategoryBO beforeData = result.getBeforeData();
        if (Objects.isNull(beforeData.getName()) && Objects.isNull(beforeData.getStatus())) {
            return;
        }
        CategoryBO afterData = result.getAfterData();
        EsGoodsBO esGoodsBO = new EsGoodsBO();
        if (StrUtil.isNotBlank(beforeData.getName())) {
            insertCategoryName(esGoodsBO, afterData);
        }
        // 更新分类列表下商品的状态
        if (Objects.nonNull(beforeData.getStatus())) {
            esGoodsBO.setSpuStatus(beforeData.getStatus());
        }
        List<Long> spuIds = getSpuIdsByCategoryId(afterData);
        goodsUpdateManager.esUpdateSpuBySpuIds(spuIds, esGoodsBO);
    }

    /**
     * 插入需要修改的分类名称
     *
     * @param esGoodsBO
     * @param afterData
     */
    private void insertCategoryName(EsGoodsBO esGoodsBO, CategoryBO afterData) {
        // 平台分类
        if (Objects.equals(Constant.PLATFORM_SHOP_ID, afterData.getShopId())) {
            if (afterData.getLevel().equals(CategoryLevel.First.value())) {
                esGoodsBO.setPrimaryCategoryName(afterData.getName());
            } else if (afterData.getLevel().equals(CategoryLevel.SECOND.value())) {
                esGoodsBO.setSecondaryCategoryName(afterData.getName());
            } else {
                esGoodsBO.setCategoryName(afterData.getName());
            }
        }
        // 商家分类
        else {
            if (afterData.getLevel().equals(CategoryLevel.First.value())) {
                esGoodsBO.setShopPrimaryCategoryName(afterData.getName());
            } else if (afterData.getLevel().equals(CategoryLevel.SECOND.value())) {
                esGoodsBO.setShopSecondaryCategoryName(afterData.getName());
            }
        }

    }


    /**
     * 根据分类信息，获取分类下的商品Id列表
     *
     * @param category
     * @return
     */
    private List<Long> getSpuIdsByCategoryId(CategoryBO category) {
        List<Long> spuIds = new ArrayList<>();
        ServerResponseEntity<List<Long>> spuIdResponse = null;
        List<Long> categoryIds = new ArrayList<>();
        Boolean isSearch =
                (category.getShopId().equals(Constant.PLATFORM_SHOP_ID) && !category.getLevel().equals(CategoryLevel.THIRD.value())) && (!category.getShopId().equals(Constant.PLATFORM_SHOP_ID) && category.getLevel().equals(CategoryLevel.First.value()));
        // 平台分类
        if (isSearch) {
            ServerResponseEntity<List<Long>> categoryResponse =
                    categoryFeignClient.listCategoryId(category.getCategoryId());
            categoryIds.addAll(categoryResponse.getData());
        } else {
            categoryIds.add(category.getCategoryId());
        }

        if (Objects.equals(category.getShopId(), Constant.PLATFORM_SHOP_ID)) {
            spuIdResponse = goodsFeignClient.getSpuIdsByCategoryIds(categoryIds);
        } else {
            spuIdResponse = goodsFeignClient.getSpuIdsByShopCategoryIds(categoryIds);
        }
        spuIds.addAll(spuIdResponse.getData());
        return spuIds;
    }
}
