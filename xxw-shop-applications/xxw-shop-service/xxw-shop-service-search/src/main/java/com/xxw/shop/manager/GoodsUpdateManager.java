package com.xxw.shop.manager;

import cn.hutool.core.collection.CollUtil;
import com.xxw.shop.constant.EsIndexEnum;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import com.xxw.shop.module.common.json.JsonUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodsUpdateManager {

    private static final Logger log = LoggerFactory.getLogger(GoodsUpdateManager.class);

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 批量更新es中的商品信息
     *
     * @param spuIds    spuId列表
     * @param esGoodsBO 更新的数据
     */
    public void esUpdateSpuBySpuIds(List<Long> spuIds, EsGoodsBO esGoodsBO) {
        String source = JsonUtil.toJson(esGoodsBO);
        try {
            if (CollUtil.isEmpty(spuIds)) {
                return;
            }
            BulkRequest request = new BulkRequest();
            // 准备更新的数据
            for (Long spuId : spuIds) {
                request.add(new UpdateRequest(EsIndexEnum.GOODS.value(), String.valueOf(spuId)).doc(source,
                        XContentType.JSON));
            }
            //更新
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                log.error("elasticsearch异常 错误：{}", bulkResponse.buildFailureMessage());
            }
        } catch (Exception e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
        }
    }
}
