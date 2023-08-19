package com.xxw.shop.listener;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.xxw.shop.bo.SpuExtensionBO;
import com.xxw.shop.constant.EsIndexEnum;
import com.xxw.shop.constant.SearchBusinessError;
import com.xxw.shop.module.cache.tool.IGlobalRedisCacheManager;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.starter.canal.model.CanalBinLogResult;
import com.xxw.shop.starter.canal.support.processor.BaseCanalBinlogEventProcessor;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpuExtensionCanalListener extends BaseCanalBinlogEventProcessor<SpuExtensionBO> {

    private static final Logger log = LoggerFactory.getLogger(SpuExtensionCanalListener.class);

    @Resource
    private IGlobalRedisCacheManager globalRedisCacheManager;

    @Resource
    private ElasticsearchClient client;

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

        Long spuId = afterData.getSpuId();

        // 清除缓存
        globalRedisCacheManager.evictCache("shop_goods:spu_extension:", spuId.toString());

        EsGoodsBO esGoodsBO = new EsGoodsBO();
        // 可售库存
        esGoodsBO.setSpuId(spuId);
        esGoodsBO.setStock(afterData.getStock());
        esGoodsBO.setHasStock(afterData.getStock() != 0);
        esGoodsBO.setSaleNum(afterData.getSaleNum());
        try {
            UpdateResponse<EsGoodsBO> updateResponse = client.update(u ->
                    // 索引
                    u.index(EsIndexEnum.GOODS.value())
                            // ID
                            .id(String.valueOf(spuId))
                            // 文档
                            .doc(esGoodsBO), EsGoodsBO.class);
            log.info("elasticsearch返回结果：" + updateResponse.toString());
        } catch (Exception e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
            throw new BusinessException(SearchBusinessError.SEARCH_00002);
        }
    }
}
