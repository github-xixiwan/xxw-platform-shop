//package com.xxw.shop.manager;
//
//import cn.hutool.core.util.StrUtil;
//import com.xxw.shop.api.search.dto.OrderSearchDTO;
//import com.xxw.shop.api.search.vo.EsOrderInfoVO;
//import com.xxw.shop.api.search.vo.EsPageVO;
//import com.xxw.shop.constant.EsIndexEnum;
//import com.xxw.shop.module.common.json.JsonUtil;
//import jakarta.annotation.Resource;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.apache.lucene.search.join.ScoreMode;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.NestedQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.RangeQueryBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//@Component
//public class OrderSearchManager {
//
//    private static final Logger log = LoggerFactory.getLogger(OrderSearchManager.class);
//
//    @Resource
//    private RestHighLevelClient restHighLevelClient;
//
//    /**
//     * 通过搜索信息分页搜索es数据的信息
//     *
//     * @return 搜索结果
//     * @dto dto 订单搜索条件
//     */
//    public EsPageVO<EsOrderInfoVO> pageSearchResult(OrderSearchDTO dto) {
//        //1、动态构建出查询需要的DSL语句
//        EsPageVO<EsOrderInfoVO> result = null;
//
//        //1、准备检索请求
//        SearchRequest searchRequest = buildSearchRequest(dto);
//
//        try {
//            //2、执行检索请求
//            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//
//            log.debug("搜索返回结果：" + response.toString());
//
//            //3、分析响应数据，封装成我们需要的格式
//            result = buildSearchResult(dto, response);
//        } catch (IOException e) {
//            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
//        }
//        return result;
//    }
//
//    /**
//     * 构建结果数据
//     */
//    private EsPageVO<EsOrderInfoVO> buildSearchResult(OrderSearchDTO dto, SearchResponse response) {
//        EsPageVO<EsOrderInfoVO> esEsPageVO = new EsPageVO<>();
//
//        //1、返回的所有查询到的商品
//        SearchHits hits = response.getHits();
//        List<EsOrderInfoVO> goodsSearchs = getEsOrderBOList(response);
//        esEsPageVO.setRecords(goodsSearchs);
//
//
//        //===============分页信息====================//
//        //总记录数
//        long total = hits.getTotalHits().value;
//        esEsPageVO.setTotalRow(total);
//        // 总页码
//        int totalPage = (int) total % dto.getPageSize() == 0 ? (int) total / dto.getPageSize() :
//                ((int) total / dto.getPageSize() + 1);
//        esEsPageVO.setTotalPage(totalPage);
//        return esEsPageVO;
//    }
//
//    private List<EsOrderInfoVO> getEsOrderBOList(SearchResponse response) {
//
//        return getOrderListByResponse(response.getHits().getHits());
//    }
//
//    /**
//     * 从es返回的数据中获取spu列表
//     *
//     * @return
//     * @dto hits es返回的数据
//     */
//    private List<EsOrderInfoVO> getOrderListByResponse(SearchHit[] hits) {
//        List<EsOrderInfoVO> esOrders = new ArrayList<>();
//        for (SearchHit hit : hits) {
//            EsOrderInfoVO esOrder = JsonUtil.fromJson(hit.getSourceAsString(), EsOrderInfoVO.class);
//            esOrders.add(esOrder);
//        }
//        return esOrders;
//    }
//
//
//    /**
//     * 准备检索请求
//     *
//     * @return
//     * @dto dto 分页参数
//     * @dto dto   搜索参数
//     */
//    private SearchRequest buildSearchRequest(OrderSearchDTO dto) {
//
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        // 构建bool-query
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//
//        // 过滤
//        filterQueryIfNecessary(dto, boolQueryBuilder);
//
//        // 关键字搜索
//        keywordSearch(dto, boolQueryBuilder);
//
//        // 排序
//        sort(searchSourceBuilder, boolQueryBuilder);
//
//        if (dto.getPageNumber() > 0) {
//            searchSourceBuilder.from((dto.getPageNumber() - 1) * dto.getPageSize());
//            searchSourceBuilder.size(dto.getPageSize());
//        }
//
//        log.debug("构建的DSL语句 {}", searchSourceBuilder.toString());
//
//        return new SearchRequest(new String[]{EsIndexEnum.ORDER.value()}, searchSourceBuilder);
//    }
//
//
//    /**
//     * 关键字搜索
//     */
//    private void keywordSearch(OrderSearchDTO dto, BoolQueryBuilder boolQueryBuilder) {
//
//        // 创建查询语句 ES中must和should不能同时使用 同时使用should失效 嵌套多个must 将should条件拼接在一个must中即可
//
//
//        BoolQueryBuilder keywordShouldQuery = QueryBuilders.boolQuery();
//
//        // 订单id
//        if (dto.getOrderId() != null) {
//            keywordShouldQuery.should(QueryBuilders.matchQuery("orderId", dto.getOrderId()));
//        }
//
//        // 店铺名称
//        if (StrUtil.isNotBlank(dto.getShopName())) {
//            keywordShouldQuery.should(QueryBuilders.matchQuery("shopName", dto.getShopName()));
//        }
//
//        if (StrUtil.isNotBlank(dto.getSpuName())) {
//            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//            // 订单项商品名称搜索
//            if (StrUtil.isNotBlank(dto.getSpuName())) {
//                boolQuery.must(QueryBuilders.matchQuery("orderItems.spuName", dto.getSpuName()));
//            }
//            NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("orderItems", boolQuery, ScoreMode.None);
//            keywordShouldQuery.should(nestedQueryBuilder);
//        }
//
//
//        //收货人姓名
//        if (StrUtil.isNotBlank(dto.getConsignee())) {
//            keywordShouldQuery.should(QueryBuilders.matchQuery("consignee", dto.getConsignee()));
//        }
//
//        //收货人手机号
//        if (StrUtil.isNotBlank(dto.getMobile())) {
//            keywordShouldQuery.should(QueryBuilders.matchQuery("mobile", dto.getMobile()));
//        }
//
//
//        boolQueryBuilder.must(keywordShouldQuery);
//    }
//
//    /**
//     * 进行排序
//     */
//    private void sort(SearchSourceBuilder searchSourceBuilder, BoolQueryBuilder boolQueryBuilder) {
//        searchSourceBuilder.sort("createTime", SortOrder.DESC);
//        searchSourceBuilder.query(boolQueryBuilder);
//    }
//
//    /**
//     * 过滤查询条件，如果有必要的话
//     *
//     * @dto dto            查询条件
//     * @dto boolQueryBuilder 组合进boolQueryBuilder
//     */
//    private void filterQueryIfNecessary(OrderSearchDTO dto, BoolQueryBuilder boolQueryBuilder) {
//        // 店铺id
//        if (Objects.nonNull(dto.getShopId())) {
//            boolQueryBuilder.filter(QueryBuilders.termQuery("shopId", dto.getShopId()));
//        }
//
//        // 用户id
//        if (Objects.nonNull(dto.getUserId())) {
//            boolQueryBuilder.filter(QueryBuilders.termQuery("userId", dto.getUserId()));
//        }
//
//        // 订单状态 参考OrderStatus
//        if (Objects.nonNull(dto.getStatus()) && !Objects.equals(dto.getStatus(), 0)) {
//            boolQueryBuilder.filter(QueryBuilders.termQuery("status", dto.getStatus()));
//        }
//
//        // 是否已经支付，1：已经支付过，0：，没有支付过
//        if (Objects.nonNull(dto.getIsPayed())) {
//            boolQueryBuilder.filter(QueryBuilders.termQuery("isPayed", dto.getIsPayed()));
//        }
//
//        // 开始时间 - 结束时间
//        if (dto.getStartTime() != null || dto.getEndTime() != null) {
//            // 销售价
//            String createTime = "createTime";
//            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(createTime);
//            if (dto.getStartTime() != null) {
//                rangeQueryBuilder.gte(dto.getStartTime());
//            }
//            if (dto.getEndTime() != null) {
//                rangeQueryBuilder.lte(dto.getEndTime());
//            }
//            boolQueryBuilder.filter(rangeQueryBuilder);
//        }
//
//        // 物流类型 3：无需快递
//        if (Objects.equals(dto.getDeliveryType(), 1)) {
//            boolQueryBuilder.filter(QueryBuilders.termQuery("deliveryType", dto.getDeliveryType()));
//        }
//
//    }
//
//}
