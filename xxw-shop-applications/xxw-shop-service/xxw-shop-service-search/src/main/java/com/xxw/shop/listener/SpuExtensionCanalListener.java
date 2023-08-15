package com.xxw.shop.listener;

import com.xxw.shop.bo.SpuExtensionBO;
import com.xxw.shop.constant.EsIndexEnum;
import com.xxw.shop.constant.SearchBusinessError;
import com.xxw.shop.module.cache.tool.IGlobalRedisCacheManager;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.json.JsonUtil;
import com.xxw.shop.starter.canal.model.CanalBinLogResult;
import com.xxw.shop.starter.canal.support.processor.BaseCanalBinlogEventProcessor;
import jakarta.annotation.Resource;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SpuExtensionCanalListener extends BaseCanalBinlogEventProcessor<SpuExtensionBO> {

    private static final Logger log = LoggerFactory.getLogger(SpuExtensionCanalListener.class);

    @Resource
    private IGlobalRedisCacheManager globalRedisCacheManager;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 插入商品，此时插入es
     */
    @Override
    protected void processInsertInternal(CanalBinLogResult<SpuExtensionBO> result) {
        System.out.println();
    }

    /**
     * 更新商品，删除商品索引，再重新构建一个
     */
    @Override
    protected void processUpdateInternal(CanalBinLogResult<SpuExtensionBO> result) {
        // 更新之后的数据
        SpuExtensionBO afterData = result.getAfterData();

        // 清除缓存
        globalRedisCacheManager.evictCache("shop_goods:spu_extension:", afterData.getSpuId().toString());

        UpdateRequest request = new UpdateRequest(EsIndexEnum.PRODUCT.value(), String.valueOf(afterData.getSpuId()));

        EsGoodsBO esGoodsBO = new EsGoodsBO();
        // 可售库存
        esGoodsBO.setSpuId(afterData.getSpuId());
        esGoodsBO.setStock(afterData.getStock());
        esGoodsBO.setHasStock(afterData.getStock() != 0);
        esGoodsBO.setSaleNum(afterData.getSaleNum());

        request.doc(JsonUtil.toJson(esGoodsBO), XContentType.JSON);
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            log.info(updateResponse.toString());
        } catch (IOException e) {
            log.error(e.toString());
            throw new BusinessException(SearchBusinessError.SEARCH_00005);
        }
    }
}
