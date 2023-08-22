package com.xxw.shop.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import com.xxw.shop.constant.EsIndexEnum;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoodsUpdateManager {

    private static final Logger log = LoggerFactory.getLogger(GoodsUpdateManager.class);

    @Resource
    private ElasticsearchClient client;

    /**
     * 批量更新es中的商品信息
     *
     * @param spuIds    spuId列表
     * @param esGoodsBO 更新的数据
     */
    public void esUpdateSpuBySpuIds(List<Long> spuIds, EsGoodsBO esGoodsBO) {
        String source = JSONUtil.toJsonStr(esGoodsBO);
        try {
            if (CollUtil.isEmpty(spuIds)) {
                return;
            }
            List<BulkOperation> bulkOperationArrayList = new ArrayList<>();
            // 准备更新的数据
            for (Long spuId : spuIds) {
                bulkOperationArrayList.add(BulkOperation.of(o -> o.index(i -> i.id(String.valueOf(spuId)).document(source))));
            }
            //更新
            BulkResponse bulkResponse =
                    client.bulk(b -> b.index(EsIndexEnum.GOODS.value()).operations(bulkOperationArrayList));
            if (bulkResponse.errors()) {
                log.error("elasticsearch异常 错误：{}", bulkResponse.toString());
            }
        } catch (Exception e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
        }
    }
}
