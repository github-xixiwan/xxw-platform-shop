package com.xxw.shop.listener;

import cn.hutool.core.util.StrUtil;
import com.xxw.shop.api.goods.feign.GoodsFeignClient;
import com.xxw.shop.bo.BrandBO;
import com.xxw.shop.manager.GoodsUpdateManager;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.starter.canal.model.CanalBinLogResult;
import com.xxw.shop.starter.canal.support.processor.BaseCanalBinlogEventProcessor;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class BrandCanalListener extends BaseCanalBinlogEventProcessor<BrandBO> {

    private static final Logger log = LoggerFactory.getLogger(BrandCanalListener.class);

    @Resource
    private GoodsUpdateManager goodsUpdateManager;

    @Resource
    private GoodsFeignClient goodsFeignClient;

    /**
     * 新增品牌
     */
    @Override
    protected void processInsertInternal(CanalBinLogResult<BrandBO> brandResult) {

    }

    /**
     * 更新品牌
     *
     * @param result
     */
    @Override
    protected void processUpdateInternal(CanalBinLogResult<BrandBO> result) {
        BrandBO beforeData = result.getBeforeData();
        if (Objects.isNull(beforeData.getName()) && StrUtil.isBlank(beforeData.getImgUrl())) {
            return;
        }
        BrandBO afterData = result.getAfterData();
        EsGoodsBO esGoodsBO = new EsGoodsBO();
        if (StrUtil.isNotBlank(beforeData.getName())) {
            esGoodsBO.setBrandName(afterData.getName());
        }
        if (Objects.nonNull(beforeData.getImgUrl())) {
            esGoodsBO.setBrandImg(afterData.getImgUrl());
        }
        ServerResponseEntity<List<Long>> responseData = goodsFeignClient.getSpuIdsByBrandId(afterData.getBrandId());
        goodsUpdateManager.esUpdateSpuBySpuIds(responseData.getData(), esGoodsBO);
    }
}
