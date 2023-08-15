package com.xxw.shop.manager;

import com.xxw.shop.constant.EsIndexEnum;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.json.JsonUtil;
import jakarta.annotation.Resource;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodsUpdateManager {

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
            BulkRequest request = new BulkRequest();
            // 准备更新的数据
            for (Long spuId : spuIds) {
                request.add(new UpdateRequest(EsIndexEnum.PRODUCT.value(), String.valueOf(spuId)).doc(source,
                        XContentType.JSON));
            }
            //更新
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                throw new BusinessException(bulkResponse.buildFailureMessage());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
