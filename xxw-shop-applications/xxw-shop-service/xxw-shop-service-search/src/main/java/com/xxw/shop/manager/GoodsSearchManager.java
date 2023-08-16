package com.xxw.shop.manager;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xxw.shop.api.search.dto.GoodsSearchDTO;
import com.xxw.shop.api.search.vo.*;
import com.xxw.shop.constant.*;
import com.xxw.shop.module.common.constant.StatusEnum;
import com.xxw.shop.module.common.json.JsonUtil;
import com.xxw.shop.vo.SpuAdminVO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedTopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class GoodsSearchManager {

    private static final Logger log = LoggerFactory.getLogger(GoodsSearchManager.class);


    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 通过搜索信息分页搜索es数据并聚合返回的信息
     *
     * @return 搜索结果
     * @dto dto 商品搜索条件
     */
    public EsPageVO<EsGoodsSearchVO> page(GoodsSearchDTO dto) {
        dto.setSpuStatus(StatusEnum.ENABLE.value());
        dto.setSearchType(SearchTypeEnum.CONSUMER.value());
        SearchResponse response = pageSearchResult(dto, Boolean.TRUE);
        return buildSearchResult(dto, response);
    }

    /**
     * 通过搜索信息分页搜索es数据的信息
     *
     * @return 搜索结果
     * @dto dto 分页数据
     * @dto dto 商品搜索条件
     */
    public EsPageVO<EsGoodsSearchVO> simplePage(GoodsSearchDTO dto) {
        dto.setSpuStatus(StatusEnum.ENABLE.value());
        dto.setSearchType(SearchTypeEnum.CONSUMER.value());
        SearchResponse response = pageSearchResult(dto, Boolean.FALSE);
        return buildSearchResult(dto, response);
    }

    public List<EsSpuVO> list(GoodsSearchDTO dto) {
        //1、准备检索请求
        dto.setPageNumber(0);
        SearchRequest searchRequest = buildSearchRequest(dto, Boolean.TRUE);
        List<EsSpuVO> spuList = null;
        try {
            //2、执行检索请求
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            log.debug("搜索返回结果：" + response.toString());

            //1、返回的所有查询到的商品
            spuList = getSpuListByResponse(response.getHits().getHits());
        } catch (IOException e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
        }
        return spuList;
    }

    /**
     * 通过搜索信息分页搜索es数据的信息
     *
     * @return 搜索结果
     * @dto dto 分页数据
     * @dto dto 商品搜索条件
     * @dto isAgg true:聚合搜索  false:非聚合搜索  null:非聚合搜索
     */
    private SearchResponse pageSearchResult(GoodsSearchDTO dto, Boolean isAgg) {
        //1、准备检索请求
        SearchRequest searchRequest = buildSearchRequest(dto, isAgg);
        SearchResponse response = null;

        try {
            //2、执行检索请求
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            log.debug("搜索返回结果：" + response.toString());
        } catch (IOException e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
        }
        return response;
    }

    /**
     * 构建结果数据
     */
    private EsPageVO<EsGoodsSearchVO> buildSearchResult(GoodsSearchDTO dto, SearchResponse response) {
        EsPageVO<EsGoodsSearchVO> esPageVO = new EsPageVO<>();

        //1、返回的所有查询到的商品
        SearchHits hits = response.getHits();
        List<EsGoodsSearchVO> goodsSearchs = getGoodsSearchVOList(response);
        esPageVO.setRecords(goodsSearchs);


        //===============分页信息====================//
        //总记录数
        long total = hits.getTotalHits().value;
        esPageVO.setTotalRow(total);
        // 总页码
        int totalPages = (int) total % dto.getPageSize() == 0 ? (int) total / dto.getPageSize() :
                ((int) total / dto.getPageSize() + 1);
        esPageVO.setTotalPage(totalPages);
        return esPageVO;
    }

    private List<EsGoodsSearchVO> getGoodsSearchVOList(SearchResponse response) {
        EsGoodsSearchVO goodsSearchVO = new EsGoodsSearchVO();
        //===============spu列表信息====================//
        goodsSearchVO.setSpus(getSpuListByResponse(response.getHits().getHits()));

        //===============聚合信息====================//
        Aggregations aggregations = response.getAggregations();
        if (Objects.nonNull(aggregations)) {
            loadAggregationsData(goodsSearchVO, aggregations);
        }

        List<EsGoodsSearchVO> goodsSearches = new ArrayList<>();
        goodsSearches.add(goodsSearchVO);
        return goodsSearches;
    }

    private void loadAggregationsData(EsGoodsSearchVO goodsSearchVO, Aggregations aggregations) {
        //===============品牌信息====================//
        ParsedLongTerms brandTerms = aggregations.get(EsConstant.BRANDS);
        if (Objects.nonNull(brandTerms)) {
            goodsSearchVO.setBrands(new ArrayList<>());
            List<? extends Terms.Bucket> brandsBuckets = brandTerms.getBuckets();
            for (Terms.Bucket bucket : brandsBuckets) {
                BrandSearchVO brandSearchVO = new BrandSearchVO();
                brandSearchVO.setBrandId(Long.valueOf(bucket.getKey().toString()));
                brandSearchVO.setBrandImg(getValuesByBucket(bucket, EsConstant.BRAND_IMG));
                brandSearchVO.setBrandName(getValuesByBucket(bucket, EsConstant.BRAND_NAME));
                goodsSearchVO.getBrands().add(brandSearchVO);
            }
        }
        //===============分类信息====================//
        goodsSearchVO.setCategorys(new ArrayList<>());
        ParsedLongTerms categoriesTerms = null;
        String categoryName = null;
        // 平台分类
        if (Objects.nonNull(aggregations.get(EsConstant.CATEGORIES))) {
            categoryName = EsConstant.CATEGORY_NAME;
            categoriesTerms = aggregations.get(EsConstant.CATEGORIES);
        }
        // 店铺分类
        else {
            categoryName = EsConstant.SHOP_CATEGORY_NAME;
            categoriesTerms = aggregations.get(EsConstant.SHOP_CATEGORIES);
        }
        if (Objects.nonNull(categoriesTerms)) {
            List<? extends Terms.Bucket> categoriesBuckets = categoriesTerms.getBuckets();
            for (Terms.Bucket bucket : categoriesBuckets) {
                CategorySearchVO categoryVO = new CategorySearchVO();
                categoryVO.setCategoryId((Long) bucket.getKey());
                categoryVO.setName(getValuesByBucket(bucket, categoryName));
                goodsSearchVO.getCategorys().add(categoryVO);
            }
        }

        //===============店铺信息====================//
        ParsedLongTerms shopTerms = aggregations.get(EsConstant.SHOP);
        if (Objects.nonNull(shopTerms)) {
            List<? extends Terms.Bucket> shopBuckets = shopTerms.getBuckets();
            for (Terms.Bucket bucket : shopBuckets) {
                goodsSearchVO.setShopInfo(new ShopInfoSearchVO());
                goodsSearchVO.getShopInfo().setShopId(Long.valueOf(bucket.getKey().toString()));
            }
        }
        //===============属性信息====================//
        goodsSearchVO.setAttrs(new ArrayList<>());
        ParsedNested attrsNested = aggregations.get(EsConstant.ATTRS);
        if (Objects.nonNull(attrsNested)) {
            Aggregations attrIdAggregations = attrsNested.getAggregations();
            ParsedLongTerms attrIdsTrems = attrIdAggregations.get(EsConstant.ATTR_IDS);
            List<? extends Terms.Bucket> attrsBuckets = attrIdsTrems.getBuckets();
            for (Terms.Bucket bucket : attrsBuckets) {
                ParsedLongTerms attrLongTerms = bucket.getAggregations().get(EsConstant.ATTR_VALUE_IDS);
                AttrSearchVO attrSearchVO = null;
                for (Terms.Bucket attrValueBucket : attrLongTerms.getBuckets()) {
                    ParsedTopHits parsedTopHits = attrValueBucket.getAggregations().get(EsConstant.TOP_HITS_DATA);
                    for (SearchHit hit : parsedTopHits.getHits().getHits()) {
                        if (Objects.isNull(attrSearchVO)) {
                            attrSearchVO = JsonUtil.fromJson(hit.getSourceAsString(), AttrSearchVO.class);
                            attrSearchVO.setAttrId(Long.valueOf(bucket.getKey().toString()));
                            attrSearchVO.setAttrValues(new ArrayList<>());
                        }
                        AttrValueSearchVO attrValueSearchVO = JsonUtil.fromJson(hit.getSourceAsString(),
                                AttrValueSearchVO.class);
                        attrSearchVO.getAttrValues().add(attrValueSearchVO);
                    }
                }
                goodsSearchVO.getAttrs().add(attrSearchVO);
            }
        }
    }

    /**
     * 从es返回的数据中获取spu列表
     *
     * @return
     * @dto hits es返回的数据
     */
    public List<EsSpuVO> getSpuListByResponse(SearchHit[] hits) {
        List<EsSpuVO> spus = new ArrayList<>();
        for (SearchHit hit : hits) {
            EsSpuVO spuSearchVO = JsonUtil.fromJson(hit.getSourceAsString(), EsSpuVO.class);
            spus.add(spuSearchVO);
        }
        return spus;
    }

    /**
     * 获取对应名称（name）的值
     *
     * @return 仅返回一个值
     * @dto bucket
     * @dto name
     */
    private String getValuesByBucket(Terms.Bucket bucket, String name) {
        String value = "";
        Aggregations categoryAggregations = bucket.getAggregations();
        ParsedStringTerms categoryNameTerms = categoryAggregations.get(name);
        List<? extends Terms.Bucket> buckets = categoryNameTerms.getBuckets();
        for (Terms.Bucket bucketValue : buckets) {
            value = bucketValue.getKey().toString();
            break;
        }
        return value;
    }

    /**
     * 准备检索请求
     *
     * @return
     * @dto dto 搜索参数
     * @dto isAgg true:聚合搜索  false:非聚合搜索  null:非聚合搜索
     */
    private SearchRequest buildSearchRequest(GoodsSearchDTO dto, Boolean isAgg) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 指定返回数组中的字段
        searchSourceBuilder.fetchSource(EsConstant.FETCH_SOURCE, null);

        // 构建bool-query
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        // 过滤
        filterQueryIfNecessary(dto, boolQueryBuilder);

        // 关键字搜索
        keywordSearch(dto, boolQueryBuilder);

        // 排序
        sort(dto, searchSourceBuilder, boolQueryBuilder);

        //分页
        if (dto.getPageNumber() > 0) {
            searchSourceBuilder.from((dto.getPageNumber() - 1) * dto.getPageSize());
            searchSourceBuilder.size(dto.getPageSize());
        }

        // 进行聚合分析
        agg(dto, searchSourceBuilder, isAgg);

        log.debug("构建的DSL语句 {}", searchSourceBuilder.toString());

        return new SearchRequest(new String[]{EsIndexEnum.GOODS.value()}, searchSourceBuilder);
    }

    /**
     * 聚合分析
     */
    private void agg(GoodsSearchDTO dto, SearchSourceBuilder searchSourceBuilder, Boolean isAgg) {

        // 店铺进行聚合
        if (dto.getKeyword() != null && dto.getKeyword().length() > 1) {
            // 按照店铺进行聚合
            TermsAggregationBuilder shop = AggregationBuilders.terms(EsConstant.SHOP).field(EsConstant.SHOP_ID).size(1);
            searchSourceBuilder.aggregation(shop);
        }

        if (Objects.isNull(isAgg) || !isAgg) {
            return;
        }

        // 按照品牌进行聚合
        TermsAggregationBuilder brands =
                AggregationBuilders.terms(EsConstant.BRANDS).field(EsConstant.BRAND_ID).size(10);
        brands.subAggregation(AggregationBuilders.terms(EsConstant.BRAND_NAME).field(EsConstant.BRAND_NAME).size(1));
        brands.subAggregation(AggregationBuilders.terms(EsConstant.BRAND_IMG).field(EsConstant.BRAND_IMG).size(1));
        searchSourceBuilder.aggregation(brands);

        // 搜索平台商品，按照平台分类信息进行聚合
        if (Objects.isNull(dto.getShopId())) {
            TermsAggregationBuilder categories =
                    AggregationBuilders.terms(EsConstant.CATEGORIES).field(EsConstant.CATEGORY_ID).size(10);
            categories.subAggregation(AggregationBuilders.terms(EsConstant.CATEGORY_NAME).field(EsConstant.CATEGORY_NAME).size(1));
            searchSourceBuilder.aggregation(categories);
        }
        // 搜索店铺中的商品，按照店铺分类信息进行聚合
        else {
            TermsAggregationBuilder categories =
                    AggregationBuilders.terms(EsConstant.SHOP_CATEGORIES).field(EsConstant.SHOP_CATEGORY_ID).size(10);
            categories.subAggregation(AggregationBuilders.terms(EsConstant.SHOP_CATEGORY_NAME).field(EsConstant.SHOP_CATEGORY_NAME).size(1));
            searchSourceBuilder.aggregation(categories);
        }

        // 按照属性信息进行聚合
        NestedAggregationBuilder attrs = AggregationBuilders.nested(EsConstant.ATTRS, EsConstant.ATTRS);

        // 按照属性ID进行聚合
        TermsAggregationBuilder attrIds =
                AggregationBuilders.terms(EsConstant.ATTR_IDS).field(EsConstant.ATTR_ATTR_ID).size(10);
        attrs.subAggregation(attrIds);
        TermsAggregationBuilder attrValueIds =
                AggregationBuilders.terms(EsConstant.ATTR_VALUE_IDS).field(EsConstant.ATTR_ATTR_VALUE_ID).size(10);
        attrIds.subAggregation(attrValueIds);
        String[] include1 = {EsConstant.ATTR_ATTR_NAME, EsConstant.ATTR_ATTR_VALUE_ID, EsConstant.ATTR_ATTR_VALUE_NAME};
        attrValueIds.subAggregation(AggregationBuilders.topHits(EsConstant.TOP_HITS_DATA).fetchSource(include1, null).sort(EsConstant.ATTR_ATTR_VALUE_NAME, SortOrder.ASC).size(1));

        searchSourceBuilder.aggregation(attrs);
    }


    /**
     * 关键字搜索
     */
    private void keywordSearch(GoodsSearchDTO dto, BoolQueryBuilder boolQueryBuilder) {
        if (StrUtil.isBlank(dto.getKeyword())) {
            return;
        }
        // 创建查询语句 ES中must和should不能同时使用 同时使用should失效 嵌套多个must 将should条件拼接在一个must中即可
        BoolQueryBuilder keywordShouldQuery = QueryBuilders.boolQuery()
                // 提升商品名称搜索的权重
                .should(QueryBuilders.matchQuery(EsConstant.SPU_NAME, dto.getKeyword()).boost(6));

        if (dto.getKeyword().length() > 1) {
            // 卖点，不分词
            keywordShouldQuery.should(QueryBuilders.matchPhraseQuery(EsConstant.SELLING_POINT, dto.getKeyword()).boost(3))
                    // 店铺名，不分词
                    .should(QueryBuilders.matchPhraseQuery(EsConstant.SHOP_NAME, dto.getKeyword()));
        }
        boolQueryBuilder.must(keywordShouldQuery);
    }

    /**
     * 进行排序
     */
    private void sort(GoodsSearchDTO dto, SearchSourceBuilder searchSourceBuilder, BoolQueryBuilder boolQueryBuilder) {
        //排序 如果排序规则设为空，则按照一定的算分规则进行排序，否则按照用户指定排序规则进行排序
        if (Objects.isNull(dto.getSort())) {

            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();

            // 销量数 log1p
            ScoreFunctionBuilder<FieldValueFactorFunctionBuilder> saleNumScoreFunction =
                    new FieldValueFactorFunctionBuilder(EsConstant.SALE_NUM).modifier(FieldValueFactorFunction.Modifier.LOG1P).factor(0.1f);
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(saleNumScoreFunction));

            filterFunctionBuilders.toArray();

            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(boolQueryBuilder,
                    ArrayUtil.toArray(filterFunctionBuilders, FunctionScoreQueryBuilder.FilterFunctionBuilder.class)).scoreMode(FunctionScoreQuery.ScoreMode.SUM).boostMode(CombineFunction.SUM);


            //封装所有的查询条件(带有function score)
            searchSourceBuilder.query(functionScoreQueryBuilder);

        } else {
            for (EsGoodsSortEnum enumValue : EsGoodsSortEnum.values()) {
                if (!Objects.equals(enumValue.value(), dto.getSort())) {
                    continue;
                }
                if (EsGoodsSortEnum.isAsc(enumValue)) {
                    searchSourceBuilder.sort(enumValue.sort(), SortOrder.ASC);
                } else if (EsGoodsSortEnum.isDesc(enumValue)) {
                    searchSourceBuilder.sort(enumValue.sort(), SortOrder.DESC);
                } else {

                }
            }
            //封装所有的查询条件(没有function score)
            searchSourceBuilder.query(boolQueryBuilder);
        }
    }

    /**
     * 过滤查询条件，如果有必要的话
     *
     * @dto dto 查询条件
     * @dto boolQueryBuilder 组合进boolQueryBuilder
     */
    private void filterQueryIfNecessary(GoodsSearchDTO dto, BoolQueryBuilder boolQueryBuilder) {

        // 店铺id
        if (Objects.nonNull(dto.getShopId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(EsConstant.SHOP_ID, dto.getShopId()));
        }

        // spu状态
        List<Integer> statusList = new ArrayList<>();
        if (Objects.nonNull(dto.getSpuStatus())) {
            statusList.add(dto.getSpuStatus());
        } else if (Objects.equals(dto.getDataType(), DataType.SALE.value())) {
            statusList.add(StatusEnum.ENABLE.value());
        } else if (Objects.equals(dto.getDataType(), DataType.DISABLE.value())) {
            statusList.add(StatusEnum.DISABLE.value());
        } else {
            statusList.add(StatusEnum.ENABLE.value());
            statusList.add(StatusEnum.DISABLE.value());
        }
        BoolQueryBuilder should = QueryBuilders.boolQuery().should(QueryBuilders.termsQuery(EsConstant.SPU_STATUS,
                statusList));
        boolQueryBuilder.filter(should);

        this.categoryFilterQuery(dto, boolQueryBuilder);

        // 是否有库存
        if (Objects.nonNull(dto.getHasStock())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(EsConstant.HAS_STOCK, Objects.equals(dto.getHasStock(),
                    1)));
        }

        // 商品类型
        if (Objects.nonNull(dto.getSelfShop())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(EsConstant.SHOP_TYPE, dto.getSelfShop()));
        }

        // 品牌
        if (StrUtil.isNotBlank(dto.getBrandIds())) {
            String[] ids = dto.getBrandIds().split(EsConstant.CONNECTION_SYMBOLS);
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            for (String brandId : ids) {
                boolQuery.should(QueryBuilders.termsQuery(EsConstant.BRAND_ID, brandId));
                boolQueryBuilder.filter(boolQuery);
            }
        }

        // spuId列表
        if (CollectionUtil.isNotEmpty(dto.getSpuIds())) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery(EsConstant.SPU_ID, dto.getSpuIds()));
        }

        // 规格属性值
        if (StrUtil.isNotBlank(dto.getAttrValueIds())) {
            String[] ids = dto.getAttrValueIds().split(EsConstant.CONNECTION_SYMBOLS);
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            for (String attrValueId : ids) {
                boolQuery.should(QueryBuilders.termsQuery(EsConstant.ATTR_ATTR_VALUE_ID, attrValueId));
            }
            NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery(EsConstant.ATTRS, boolQuery,
                    ScoreMode.None);
            boolQueryBuilder.filter(nestedQueryBuilder);
        }

        // 价格区间
        if (dto.getMinPrice() != null || dto.getMaxPrice() != null) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(EsConstant.PRICE_FEE);
            if (dto.getMinPrice() != null) {
                rangeQueryBuilder.gte(dto.getMinPrice());
            }
            if (dto.getMaxPrice() != null) {
                rangeQueryBuilder.lte(dto.getMaxPrice());
            }
            boolQueryBuilder.filter(rangeQueryBuilder);
        }

        // 销量区间
        if (dto.getMinSaleNum() != null || dto.getMaxSaleNum() != null) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(EsConstant.SALE_NUM);
            if (dto.getMinSaleNum() != null) {
                rangeQueryBuilder.gte(dto.getMinSaleNum());
            }
            if (dto.getMaxSaleNum() != null) {
                rangeQueryBuilder.lte(dto.getMaxSaleNum());
            }
            boolQueryBuilder.filter(rangeQueryBuilder);
        }
    }

    private void categoryFilterQuery(GoodsSearchDTO dto, BoolQueryBuilder boolQueryBuilder) {
        // 商家一级分类
        if (Objects.nonNull(dto.getShopPrimaryCategoryId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(EsConstant.SHOP_PRIMARY_CATEGORY_ID,
                    dto.getShopPrimaryCategoryId()));
        }

        //商家二级分类
        if (Objects.nonNull(dto.getShopSecondaryCategoryId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(EsConstant.SHOP_SECONDARY_CATEGORY_ID,
                    dto.getShopSecondaryCategoryId()));
        }

        // 平台一级分类
        if (Objects.nonNull(dto.getPrimaryCategoryId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(EsConstant.PRIMARY_CATEGORY_ID,
                    dto.getPrimaryCategoryId()));
        }

        // 平台三级分类
        if (Objects.nonNull(dto.getCategoryId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(EsConstant.CATEGORY_ID, dto.getCategoryId()));
        }
    }

    /**
     * 根据店铺id列表获取每个店铺的spu列表
     *
     * @return
     * @dto shopIds
     */
    public List<EsSpuVO> limitSizeListByShopIds(List<Long> shopIds, Integer size) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 构建bool-query
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 过滤
        boolQueryBuilder.filter(QueryBuilders.termsQuery(EsConstant.SHOP_ID, shopIds));
        searchSourceBuilder.query(boolQueryBuilder);
        // 聚合分析
        TermsAggregationBuilder shop = AggregationBuilders.terms(EsConstant.SHOP_COUPON).field(EsConstant.SHOP_ID);
        String[] include = {EsConstant.SPU_NAME, EsConstant.MAIN_IMG_URL, EsConstant.SHOP_ID, EsConstant.SPU_ID,
                EsConstant.PRICE_FEE};
        shop.subAggregation(AggregationBuilders.topHits(EsConstant.TOP_HITS_DATA).fetchSource(include, null).sort(EsConstant.SALE_NUM, SortOrder.DESC).size(size));
        searchSourceBuilder.aggregation(shop);
        searchSourceBuilder.size(0);
        log.debug("构建的DSL语句 {}", searchSourceBuilder.toString());
        SearchRequest searchRequest = new SearchRequest(new String[]{EsIndexEnum.GOODS.value()}, searchSourceBuilder);
        //2、执行检索请求
        SearchResponse response = null;
        List<EsSpuVO> spuList = null;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.debug("搜索返回结果：" + response.toString());
            spuList = loadSpuListByAggregations(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return spuList;
    }

    /**
     * 从聚合数据中获取商品列表
     *
     * @return
     * @dto response
     */
    private List<EsSpuVO> loadSpuListByAggregations(SearchResponse response) {
        List<EsSpuVO> spuList = new ArrayList<>();
        Aggregations aggregations = response.getAggregations();
        ParsedLongTerms shopCouponTerm = aggregations.get(EsConstant.SHOP_COUPON);
        if (Objects.nonNull(shopCouponTerm)) {
            List<? extends Terms.Bucket> buckets = shopCouponTerm.getBuckets();
            for (Terms.Bucket bucket : buckets) {
                Aggregations shopAggs = bucket.getAggregations();
                ParsedTopHits shopHits = shopAggs.get(EsConstant.TOP_HITS_DATA);
                spuList.addAll(getSpuListByResponse(shopHits.getHits().getHits()));
            }
        }
        return spuList;
    }

    /**
     * 商品管理分页搜索es数据的信息
     *
     * @return 搜索结果
     * @dto dto 分页数据
     * @dto dto 商品搜索条件
     */
    public EsPageVO<SpuAdminVO> adminPage(GoodsSearchDTO dto) {
        loadSpuStatus(dto);
        EsPageVO<SpuAdminVO> result = new EsPageVO<>();
        SearchResponse response = pageSearchResult(dto, Boolean.FALSE);
        // 商品信息
        result.setRecords(buildSpuAdminList(response));
        // 分页信息
        buildSearchPage(dto, result, response);
        return result;
    }

    private void loadSpuStatus(GoodsSearchDTO dto) {
        if (Objects.isNull(dto) || Objects.isNull(dto.getDataType())) {
            return;
        }
        Integer dataType = dto.getDataType();
        // 销售中
        if (Objects.equals(DataType.SALE.value(), dataType)) {
            dto.setSpuStatus(StatusEnum.ENABLE.value());
        }
        // 已售罄
        else if (Objects.equals(DataType.SOLD_OUT.value(), dataType)) {
            dto.setHasStock(0);
        }
    }

    /**
     * 从es返回的数据中获取spu列表
     *
     * @return
     * @dto response es返回的数据
     */
    public List<SpuAdminVO> buildSpuAdminList(SearchResponse response) {
        List<SpuAdminVO> spus = new ArrayList<>();
        String spuName = null;
        for (SearchHit hit : response.getHits()) {
            String json = hit.getSourceAsString();
            SpuAdminVO spuAdminVO = JsonUtil.fromJson(hit.getSourceAsString(), SpuAdminVO.class);
            spus.add(spuAdminVO);
        }
        return spus;
    }

    /**
     * 处理聚合国际化信息
     *
     * @return 对应语言的字段
     * @dto json 数据
     * @dto field 字段
     */
    private String handleAggregationsLang(String json, String field, String defaultField) {
        Map<String, Object> map = JsonUtil.fromJson(json, new TypeReference<Map<String, Object>>() {
        });
        Object object;
        // 找不到指定语言的数据，就查默认语言
        if (Objects.isNull(map.get(field))) {
            object = map.get(defaultField);
        }
        // 获取指定语言的数据
        else {
            object = map.get(field);
        }
        // 没有查到数据
        if (Objects.isNull(object)) {
            return null;
        }
        return object.toString();
    }

    /**
     * 构建分页数据
     *
     * @dto dto
     * @dto esPageVO
     * @dto response
     */
    private void buildSearchPage(GoodsSearchDTO dto, EsPageVO<?> esPageVO, SearchResponse response) {
        //总记录数
        long total = response.getHits().getTotalHits().value;
        esPageVO.setTotalRow(total);
        // 总页码
        int totalPages = (int) total % dto.getPageSize() == 0 ? (int) total / dto.getPageSize() :
                ((int) total / dto.getPageSize() + 1);
        esPageVO.setTotalPage(totalPages);
    }
}
