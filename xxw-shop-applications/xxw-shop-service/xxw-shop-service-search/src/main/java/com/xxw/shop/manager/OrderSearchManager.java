package com.xxw.shop.manager;

import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.google.common.collect.Lists;
import com.xxw.shop.api.search.dto.OrderSearchDTO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.constant.EsConstant;
import com.xxw.shop.constant.EsIndexEnum;
import com.xxw.shop.module.common.bo.EsOrderBO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OrderSearchManager {

    private static final Logger log = LoggerFactory.getLogger(OrderSearchManager.class);

    @Resource
    private ElasticsearchClient client;

    /**
     * 通过搜索信息分页搜索es数据并聚合返回的信息
     *
     * @return 搜索结果
     * @dto dto 订单搜索条件
     */
    public EsPageVO<EsOrderBO> page(OrderSearchDTO dto) {
        SearchResponse<EsOrderBO> searchResponse = pageSearchResult(dto);
        return buildSearchResult(dto, searchResponse);
    }

    /**
     * 通过搜索信息分页搜索es数据的信息
     *
     * @return 搜索结果
     * @dto dto 订单搜索条件
     */
    public SearchResponse<EsOrderBO> pageSearchResult(OrderSearchDTO dto) {
        //1、准备检索请求
        SearchRequest searchRequest = buildSearchRequest(dto);
        SearchResponse<EsOrderBO> searchResponse = null;
        try {
            //2、执行检索请求
            searchResponse = client.search(searchRequest, EsOrderBO.class);
            log.debug("搜索返回结果：" + searchResponse.toString());
        } catch (Exception e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
        }
        return searchResponse;
    }

    /**
     * 构建结果数据
     */
    private EsPageVO<EsOrderBO> buildSearchResult(OrderSearchDTO dto, SearchResponse<EsOrderBO> searchResponse) {
        EsPageVO<EsOrderBO> esPageVO = new EsPageVO<>();

        //1、返回的所有查询到的商品
        List<EsOrderBO> list = getEsOrderBOList(searchResponse);
        esPageVO.setRecords(list);

        // 分页信息
        buildSearchPage(dto, esPageVO, searchResponse);
        return esPageVO;
    }

    private List<EsOrderBO> getEsOrderBOList(SearchResponse<EsOrderBO> searchResponse) {
        List<EsOrderBO> list = Lists.newArrayList();
        List<Hit<EsOrderBO>> hitList = searchResponse.hits().hits();
        for (Hit<EsOrderBO> hit : hitList) {
            EsOrderBO vo = hit.source();
            list.add(vo);
        }
        return list;
    }


    /**
     * 准备检索请求
     *
     * @return
     * @dto dto 分页参数
     * @dto dto   搜索参数
     */
    private SearchRequest buildSearchRequest(OrderSearchDTO dto) {
        SearchRequest.Builder builder = new SearchRequest.Builder();

        // 构建bool-query
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        // 过滤
        filterQueryIfNecessary(dto, boolQueryBuilder);

        // 关键字搜索
        keywordSearch(dto, boolQueryBuilder);

        // 排序
        sort(builder, boolQueryBuilder);

        //分页
        if (dto.getPageNumber() > 0) {
            builder.from((dto.getPageNumber() - 1) * dto.getPageSize());
            builder.size(dto.getPageSize());
        }

        //订单表
        builder.index(EsIndexEnum.ORDER.value());

        SearchRequest build = builder.build();
        log.debug("构建的DSL语句 {}", build.toString());
        return build;
    }

    /**
     * 关键字搜索
     */
    private void keywordSearch(OrderSearchDTO dto, BoolQuery.Builder boolQueryBuilder) {

        // 创建查询语句 ES中must和should不能同时使用 同时使用should失效 嵌套多个must 将should条件拼接在一个must中即可
        BoolQuery.Builder builder = new BoolQuery.Builder();

        // 订单id
        Long orderId = dto.getOrderId();
        if (orderId != null) {
            builder.should(s -> s.match(t -> t.field(EsConstant.ORDER_ID).query(orderId)));
        }

        // 店铺名称
        String shopName = dto.getShopName();
        if (StrUtil.isNotBlank(shopName)) {
            builder.should(s -> s.match(t -> t.field(EsConstant.SHOP_NAME).query(shopName)));
        }

        // 订单项商品名称搜索
        String spuName = dto.getSpuName();
        if (StrUtil.isNotBlank(spuName)) {
            BoolQuery boolQuery =
                    BoolQuery.of(b -> b.must(s -> s.match(m -> m.field(EsConstant.ORDER_ITEMS_SPU_NAME).query(spuName))));
            NestedQuery nestedQuery =
                    NestedQuery.of(n -> n.path(EsConstant.ORDER_ITEMS).query(boolQuery._toQuery()).scoreMode(ChildScoreMode.None));
            builder.should(nestedQuery._toQuery());
        }

        //收货人姓名
        String consignee = dto.getConsignee();
        if (StrUtil.isNotBlank(consignee)) {
            builder.should(s -> s.match(t -> t.field(EsConstant.CONSIGNEE).query(consignee)));
        }

        //收货人手机号
        String mobile = dto.getMobile();
        if (StrUtil.isNotBlank(mobile)) {
            builder.should(s -> s.match(t -> t.field(EsConstant.MOBILE).query(mobile)));
        }
        boolQueryBuilder.must(builder.build()._toQuery());
    }

    /**
     * 进行排序
     */
    private void sort(SearchRequest.Builder builder, BoolQuery.Builder boolQueryBuilder) {
        List<SortOptions> list = new ArrayList<>();
        SortOptions sortOptions = SortOptions.of(s -> s.field(f -> f.field(EsConstant.ORDER_ID).order(SortOrder.Desc)));
        list.add(sortOptions);
        builder.sort(list).query(boolQueryBuilder.build()._toQuery());
    }

    /**
     * 过滤查询条件，如果有必要的话
     *
     * @dto dto            查询条件
     * @dto boolQueryBuilder 组合进boolQueryBuilder
     */
    private void filterQueryIfNecessary(OrderSearchDTO dto, BoolQuery.Builder boolQueryBuilder) {
        // 店铺id
        Long shopId = dto.getShopId();
        if (Objects.nonNull(dto.getShopId())) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.SHOP_ID).value(shopId)));
        }

        // 用户id
        Long userId = dto.getUserId();
        if (Objects.nonNull(userId)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.USER_ID).value(userId)));
        }

        // 订单状态 参考OrderStatus
        Integer status = dto.getStatus();
        if (Objects.nonNull(status) && !Objects.equals(status, 0)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.STATUS).value(status)));
        }

        // 是否已经支付，1：已经支付过，0：，没有支付过
        Integer isPayed = dto.getIsPayed();
        if (Objects.nonNull(isPayed)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.IS_PAYED).value(isPayed)));
        }

        // 物流类型 3：无需快递
        Integer deliveryType = dto.getDeliveryType();
        if (Objects.equals(deliveryType, 1)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.DELIVERY_TYPE).value(deliveryType)));
        }

    }

    /**
     * 构建分页数据
     *
     * @dto dto
     * @dto esPageVO
     * @dto response
     */
    private void buildSearchPage(OrderSearchDTO dto, EsPageVO<?> esPageVO, SearchResponse<EsOrderBO> searchResponse) {
        //===============分页信息====================//
        //总记录数
        long total = searchResponse.hits().total() != null ? searchResponse.hits().total().value() : 0;
        esPageVO.setTotalRow(total);
        // 总页码
        int totalPages = (int) total % dto.getPageSize() == 0 ? (int) total / dto.getPageSize() :
                ((int) total / dto.getPageSize() + 1);
        esPageVO.setTotalPage(totalPages);
    }
}
