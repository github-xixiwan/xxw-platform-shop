package com.xxw.shop.listener;

import cn.hutool.core.util.StrUtil;
import com.xxw.shop.api.goods.feign.GoodsFeignClient;
import com.xxw.shop.bo.ShopDetailBO;
import com.xxw.shop.manager.GoodsUpdateManager;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import com.xxw.shop.module.common.constant.StatusEnum;
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
public class ShopDetailCanalListener extends BaseCanalBinlogEventProcessor<ShopDetailBO> {

    private static final Logger log = LoggerFactory.getLogger(ShopDetailCanalListener.class);

    @Resource
    private GoodsUpdateManager goodsUpdateManager;

    @Resource
    private GoodsFeignClient goodsFeignClient;

    /**
     * 新增店铺
     */
    @Override
    protected void processInsertInternal(CanalBinLogResult<ShopDetailBO> shopDetailResult) {

    }

    /**
     * 更新店铺
     *
     * @param result
     */
    @Override
    protected void processUpdateInternal(CanalBinLogResult<ShopDetailBO> result) {
        ShopDetailBO beforeData = result.getBeforeData();
        if (Objects.isNull(beforeData.getShopName()) && StrUtil.isBlank(beforeData.getShopLogo()) && !Objects.equals(beforeData.getShopStatus(), StatusEnum.ENABLE.value())) {
            return;
        }
        ShopDetailBO afterData = result.getAfterData();
        EsGoodsBO esGoodsBO = new EsGoodsBO();
        if (StrUtil.isNotBlank(beforeData.getShopName())) {
            esGoodsBO.setShopName(afterData.getShopName());
        }
        if (Objects.nonNull(beforeData.getShopLogo())) {
            esGoodsBO.setShopImg(afterData.getShopLogo());
        }
        if (Objects.nonNull(beforeData.getShopStatus()) && Objects.equals(beforeData.getShopId(),
                StatusEnum.ENABLE.value())) {
            esGoodsBO.setSpuStatus(StatusEnum.DISABLE.value());
        }
        ServerResponseEntity<List<Long>> responseData = goodsFeignClient.getSpuIdsByShopId(afterData.getShopId());
        goodsUpdateManager.esUpdateSpuBySpuIds(responseData.getData(), esGoodsBO);
    }
}
