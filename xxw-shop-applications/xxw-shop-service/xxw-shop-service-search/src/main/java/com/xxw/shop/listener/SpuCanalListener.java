package com.xxw.shop.listener;

import com.xxw.shop.api.goods.feign.GoodsFeignClient;
import com.xxw.shop.bo.SpuBO;
import com.xxw.shop.constant.EsIndexEnum;
import com.xxw.shop.constant.SearchBusinessError;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.json.JsonUtil;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.starter.canal.model.CanalBinLogEvent;
import com.xxw.shop.starter.canal.model.CanalBinLogResult;
import com.xxw.shop.starter.canal.support.processor.BaseCanalBinlogEventProcessor;
import com.xxw.shop.starter.canal.support.processor.ExceptionHandler;
import jakarta.annotation.Resource;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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
public class SpuCanalListener extends BaseCanalBinlogEventProcessor<SpuBO> {

    private static final Logger log = LoggerFactory.getLogger(SpuCanalListener.class);

    @Resource
    private GoodsFeignClient goodsFeignClient;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 插入商品，此时插入es
     */
    @Override
    protected void processInsertInternal(CanalBinLogResult<SpuBO> result) {
        Long spuId = result.getPrimaryKey();
        ServerResponseEntity<EsGoodsBO> esGoodsBO = goodsFeignClient.loadEsGoodsBO(spuId);
        if (!esGoodsBO.isSuccess()) {
            throw new BusinessException(SearchBusinessError.SEARCH_00004);
        }

        IndexRequest request = new IndexRequest(EsIndexEnum.GOODS.value());
        request.id(String.valueOf(spuId));
        request.source(JsonUtil.toJson(esGoodsBO.getData()), XContentType.JSON);
        try {
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info(indexResponse.toString());

        } catch (IOException e) {
            log.error(e.toString());
            throw new BusinessException(SearchBusinessError.SEARCH_00002);
        }
    }

    /**
     * 更新商品，删除商品索引，再重新构建一个
     */
    @Override
    protected void processUpdateInternal(CanalBinLogResult<SpuBO> result) {
        Long spuId = result.getPrimaryKey();
        ServerResponseEntity<EsGoodsBO> esGoodsBO = goodsFeignClient.loadEsGoodsBO(spuId);
        String source = JsonUtil.toJson(esGoodsBO.getData());
        UpdateRequest request = new UpdateRequest(EsIndexEnum.GOODS.value(), String.valueOf(spuId));
        request.doc(source, XContentType.JSON);
        request.docAsUpsert(true);
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            log.info(updateResponse.toString());
        } catch (IOException e) {
            log.error(e.toString());
            throw new BusinessException(SearchBusinessError.SEARCH_00005);
        }
    }

    @Override
    protected ExceptionHandler exceptionHandler() {
        return (CanalBinLogEvent event, Throwable throwable) -> {
            throw new BusinessException(SearchBusinessError.SEARCH_00004);
        };
    }
}
