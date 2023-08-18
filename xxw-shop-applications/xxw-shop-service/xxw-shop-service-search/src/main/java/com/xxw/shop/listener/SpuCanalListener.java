package com.xxw.shop.listener;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.xxw.shop.api.goods.feign.GoodsFeignClient;
import com.xxw.shop.bo.SpuBO;
import com.xxw.shop.constant.EsIndexEnum;
import com.xxw.shop.constant.SearchBusinessError;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.starter.canal.model.CanalBinLogEvent;
import com.xxw.shop.starter.canal.model.CanalBinLogResult;
import com.xxw.shop.starter.canal.support.processor.BaseCanalBinlogEventProcessor;
import com.xxw.shop.starter.canal.support.processor.ExceptionHandler;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
    private ElasticsearchClient client;

    /**
     * 插入商品，此时插入es
     */
    @Override
    protected void processInsertInternal(CanalBinLogResult<SpuBO> result) {
        Long spuId = result.getPrimaryKey();
        ServerResponseEntity<EsGoodsBO> serverResponseEntity = goodsFeignClient.loadEsGoodsBO(spuId);
        if (!serverResponseEntity.isSuccess()) {
            throw new BusinessException(SearchBusinessError.SEARCH_00004);
        }
        try {
            IndexResponse indexResponse = client.index(i ->
                    // 索引
                    i.index(EsIndexEnum.GOODS.value())
                            // ID
                            .id(String.valueOf(spuId))
                            // 文档
                            .document(serverResponseEntity.getData()));
            log.info("elasticsearch返回结果：" + indexResponse.toString());
        } catch (IOException e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
            throw new BusinessException(SearchBusinessError.SEARCH_00002);
        }
    }

    /**
     * 更新商品，删除商品索引，再重新构建一个
     */
    @Override
    protected void processUpdateInternal(CanalBinLogResult<SpuBO> result) {
        Long spuId = result.getPrimaryKey();
        ServerResponseEntity<EsGoodsBO> serverResponseEntity = goodsFeignClient.loadEsGoodsBO(spuId);
        try {
            UpdateResponse<EsGoodsBO> updateResponse = client.update(u ->
                    // 索引
                    u.index(EsIndexEnum.GOODS.value())
                            // ID
                            .id(String.valueOf(spuId))
                            // 文档
                            .doc(serverResponseEntity.getData()), EsGoodsBO.class);
            log.info("elasticsearch返回结果：" + updateResponse.toString());
        } catch (IOException e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
            throw new BusinessException(SearchBusinessError.SEARCH_00002);
        }
    }

    @Override
    protected ExceptionHandler exceptionHandler() {
        return (CanalBinLogEvent event, Throwable throwable) -> {
            throw new BusinessException(SearchBusinessError.SEARCH_00005);
        };
    }
}
