package com.xxw.shop.listener;

import com.xxw.shop.api.order.feign.OrderFeignClient;
import com.xxw.shop.api.order.vo.OrderInfoCompleteVO;
import com.xxw.shop.bo.OrderInfoBO;
import com.xxw.shop.constant.EsIndexEnum;
import com.xxw.shop.constant.SearchBusinessError;
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
public class OrderCanalListener extends BaseCanalBinlogEventProcessor<OrderInfoBO> {

    private static final Logger log = LoggerFactory.getLogger(OrderCanalListener.class);

    @Resource
    private OrderFeignClient orderFeignClient;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 插入订单，此时插入es
     */
    @Override
    protected void processInsertInternal(CanalBinLogResult<OrderInfoBO> result) {
        Long orderId = result.getPrimaryKey();

        ServerResponseEntity<OrderInfoCompleteVO> esOrderResponse = orderFeignClient.getEsOrder(orderId);
        IndexRequest request = new IndexRequest(EsIndexEnum.ORDER.value());
        request.id(String.valueOf(orderId));
        request.source(JsonUtil.toJson(esOrderResponse.getData()), XContentType.JSON);
        try {
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info(indexResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new BusinessException(SearchBusinessError.SEARCH_00002);
        }
    }

    /**
     * 更新订单，删除订单索引，再重新构建一个
     */
    @Override
    protected void processUpdateInternal(CanalBinLogResult<OrderInfoBO> result) {
        Long orderId = result.getPrimaryKey();
        ServerResponseEntity<OrderInfoCompleteVO> esOrderResponse = orderFeignClient.getEsOrder(orderId);
        UpdateRequest request = new UpdateRequest(EsIndexEnum.ORDER.value(), String.valueOf(orderId));
        request.doc(JsonUtil.toJson(esOrderResponse.getData()), XContentType.JSON);
        request.docAsUpsert(true);
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            log.info(updateResponse.toString());
        } catch (IOException e) {
            log.error(e.toString());
            throw new BusinessException(SearchBusinessError.SEARCH_00003);
        }
    }

    @Override
    protected ExceptionHandler exceptionHandler() {
        return (CanalBinLogEvent event, Throwable throwable) -> {
            throw new BusinessException(SearchBusinessError.SEARCH_00004);
        };
    }
}
