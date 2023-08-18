package com.xxw.shop.manager;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xxw.shop.api.search.dto.GoodsSearchDTO;
import com.xxw.shop.api.search.vo.EsGoodsSearchVO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.api.search.vo.EsSpuVO;
import com.xxw.shop.constant.*;
import com.xxw.shop.module.common.constant.StatusEnum;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
    private ElasticsearchClient client;

    /**
     * 通过搜索信息分页搜索es数据并聚合返回的信息
     *
     * @return 搜索结果
     * @dto dto 商品搜索条件
     */
    public EsPageVO<EsGoodsSearchVO> page(GoodsSearchDTO dto) {
        dto.setSpuStatus(StatusEnum.ENABLE.value());
        dto.setSearchType(SearchTypeEnum.CONSUMER.value());
        SearchResponse<EsGoodsSearchVO> searchResponse = pageSearchResult(dto, Boolean.TRUE);
        return buildSearchResult(dto, searchResponse);
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
        SearchResponse<EsGoodsSearchVO> searchResponse = pageSearchResult(dto, Boolean.FALSE);
        return buildSearchResult(dto, searchResponse);
    }

    public List<EsSpuVO> list(GoodsSearchDTO dto) {
        //1、准备检索请求
        dto.setPageNumber(0);
        SearchRequest searchRequest = buildSearchRequest(dto, Boolean.TRUE);
        List<EsSpuVO> spuList = Lists.newArrayList();
        try {
            //2、执行检索请求
            SearchResponse<EsGoodsSearchVO> searchResponse = client.search(searchRequest, EsGoodsSearchVO.class);
            log.debug("搜索返回结果：" + searchResponse.toString());

            List<Hit<EsGoodsSearchVO>> hitList = searchResponse.hits().hits();
            for (Hit<EsGoodsSearchVO> hit : hitList) {
                EsGoodsSearchVO vo = hit.source();
                if (vo == null || CollectionUtil.isEmpty(vo.getSpus())) {
                    break;
                }
                spuList.addAll(vo.getSpus());
            }
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
    private SearchResponse<EsGoodsSearchVO> pageSearchResult(GoodsSearchDTO dto, Boolean isAgg) {
        //1、准备检索请求
        SearchRequest searchRequest = buildSearchRequest(dto, isAgg);
        SearchResponse<EsGoodsSearchVO> searchResponse = null;
        try {
            //2、执行检索请求
            searchResponse = client.search(searchRequest, EsGoodsSearchVO.class);
            log.debug("搜索返回结果：" + searchResponse.toString());
        } catch (IOException e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
        }
        return searchResponse;
    }

    /**
     * 构建结果数据
     */
    private EsPageVO<EsGoodsSearchVO> buildSearchResult(GoodsSearchDTO dto,
                                                        SearchResponse<EsGoodsSearchVO> searchResponse) {
        EsPageVO<EsGoodsSearchVO> esPageVO = new EsPageVO<>();

        //1、返回的所有查询到的商品
        List<EsGoodsSearchVO> list = Lists.newArrayList();
        List<Hit<EsGoodsSearchVO>> hitList = searchResponse.hits().hits();
        for (Hit<EsGoodsSearchVO> hit : hitList) {
            EsGoodsSearchVO vo = hit.source();
            list.add(vo);
        }

//        List<EsGoodsSearchVO> goodsSearchs = getGoodsSearchVOList(searchResponse);
        esPageVO.setRecords(list);

        // 分页信息
        buildSearchPage(dto, esPageVO, searchResponse);
        return esPageVO;
    }

//    private List<EsGoodsSearchVO> getGoodsSearchVOList(SearchResponse<EsGoodsSearchVO> searchResponse) {
//        List<EsGoodsSearchVO> list = Lists.newArrayList();
//        List<Hit<EsGoodsSearchVO>> hitList = searchResponse.hits().hits();
//        for (Hit<EsGoodsSearchVO> hit : hitList) {
//            EsGoodsSearchVO vo = hit.source();
//            list.add(vo);
//        }
//
//        //===============聚合信息====================//
//        Aggregations aggregations = response.getAggregations();
//        if (Objects.nonNull(aggregations)) {
//            loadAggregationsData(goodsSearchVO, aggregations);
//        }
//
//        List<EsGoodsSearchVO> goodsSearches = new ArrayList<>();
//        goodsSearches.add(goodsSearchVO);
//        return list;
//}

//    private void loadAggregationsData(EsGoodsSearchVO goodsSearchVO, Aggregations aggregations) {
//        //===============品牌信息====================//
//        ParsedLongTerms brandTerms = aggregations.get(EsConstant.BRANDS);
//        if (Objects.nonNull(brandTerms)) {
//            goodsSearchVO.setBrands(new ArrayList<>());
//            List<? extends Terms.Bucket> brandsBuckets = brandTerms.getBuckets();
//            for (Terms.Bucket bucket : brandsBuckets) {
//                BrandSearchVO brandSearchVO = new BrandSearchVO();
//                brandSearchVO.setBrandId(Long.valueOf(bucket.getKey().toString()));
//                brandSearchVO.setBrandImg(getValuesByBucket(bucket, EsConstant.BRAND_IMG));
//                brandSearchVO.setBrandName(getValuesByBucket(bucket, EsConstant.BRAND_NAME));
//                goodsSearchVO.getBrands().add(brandSearchVO);
//            }
//        }
//        //===============分类信息====================//
//        goodsSearchVO.setCategorys(new ArrayList<>());
//        ParsedLongTerms categoriesTerms = null;
//        String categoryName = null;
//        // 平台分类
//        if (Objects.nonNull(aggregations.get(EsConstant.CATEGORIES))) {
//            categoryName = EsConstant.CATEGORY_NAME;
//            categoriesTerms = aggregations.get(EsConstant.CATEGORIES);
//        }
//        // 店铺分类
//        else {
//            categoryName = EsConstant.SHOP_CATEGORY_NAME;
//            categoriesTerms = aggregations.get(EsConstant.SHOP_CATEGORIES);
//        }
//        if (Objects.nonNull(categoriesTerms)) {
//            List<? extends Terms.Bucket> categoriesBuckets = categoriesTerms.getBuckets();
//            for (Terms.Bucket bucket : categoriesBuckets) {
//                CategorySearchVO categoryVO = new CategorySearchVO();
//                categoryVO.setCategoryId((Long) bucket.getKey());
//                categoryVO.setName(getValuesByBucket(bucket, categoryName));
//                goodsSearchVO.getCategorys().add(categoryVO);
//            }
//        }
//
//        //===============店铺信息====================//
//        ParsedLongTerms shopTerms = aggregations.get(EsConstant.SHOP);
//        if (Objects.nonNull(shopTerms)) {
//            List<? extends Terms.Bucket> shopBuckets = shopTerms.getBuckets();
//            for (Terms.Bucket bucket : shopBuckets) {
//                goodsSearchVO.setShopInfo(new ShopInfoSearchVO());
//                goodsSearchVO.getShopInfo().setShopId(Long.valueOf(bucket.getKey().toString()));
//            }
//        }
//        //===============属性信息====================//
//        goodsSearchVO.setAttrs(new ArrayList<>());
//        ParsedNested attrsNested = aggregations.get(EsConstant.ATTRS);
//        if (Objects.nonNull(attrsNested)) {
//            Aggregations attrIdAggregations = attrsNested.getAggregations();
//            ParsedLongTerms attrIdsTrems = attrIdAggregations.get(EsConstant.ATTR_IDS);
//            List<? extends Terms.Bucket> attrsBuckets = attrIdsTrems.getBuckets();
//            for (Terms.Bucket bucket : attrsBuckets) {
//                ParsedLongTerms attrLongTerms = bucket.getAggregations().get(EsConstant.ATTR_VALUE_IDS);
//                AttrSearchVO attrSearchVO = null;
//                for (Terms.Bucket attrValueBucket : attrLongTerms.getBuckets()) {
//                    ParsedTopHits parsedTopHits = attrValueBucket.getAggregations().get(EsConstant.TOP_HITS_DATA);
//                    for (SearchHit hit : parsedTopHits.getHits().getHits()) {
//                        if (Objects.isNull(attrSearchVO)) {
//                            attrSearchVO = JsonUtil.fromJson(hit.getSourceAsString(), AttrSearchVO.class);
//                            attrSearchVO.setAttrId(Long.valueOf(bucket.getKey().toString()));
//                            attrSearchVO.setAttrValues(new ArrayList<>());
//                        }
//                        AttrValueSearchVO attrValueSearchVO = JsonUtil.fromJson(hit.getSourceAsString(),
//                                AttrValueSearchVO.class);
//                        attrSearchVO.getAttrValues().add(attrValueSearchVO);
//                    }
//                }
//                goodsSearchVO.getAttrs().add(attrSearchVO);
//            }
//        }
//    }

//    /**
//     * 从es返回的数据中获取spu列表
//     *
//     * @return
//     * @dto hits es返回的数据
//     */
//    public List<EsSpuVO> getSpuListByResponse(SearchHit[] hits) {
//        List<EsSpuVO> spus = new ArrayList<>();
//        for (SearchHit hit : hits) {
//            EsSpuVO spuSearchVO = JsonUtil.fromJson(hit.getSourceAsString(), EsSpuVO.class);
//            spus.add(spuSearchVO);
//        }
//        return spus;
//    }

//    /**
//     * 获取对应名称（name）的值
//     *
//     * @return 仅返回一个值
//     * @dto bucket
//     * @dto name
//     */
//    private String getValuesByBucket(Terms.Bucket bucket, String name) {
//        String value = "";
//        Aggregations categoryAggregations = bucket.getAggregations();
//        ParsedStringTerms categoryNameTerms = categoryAggregations.get(name);
//        List<? extends Terms.Bucket> buckets = categoryNameTerms.getBuckets();
//        for (Terms.Bucket bucketValue : buckets) {
//            value = bucketValue.getKey().toString();
//            break;
//        }
//        return value;
//    }

    /**
     * 准备检索请求
     *
     * @return
     * @dto dto 搜索参数
     * @dto isAgg true:聚合搜索  false:非聚合搜索  null:非聚合搜索
     */
    private SearchRequest buildSearchRequest(GoodsSearchDTO dto, Boolean isAgg) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        // 过滤
        filterQueryIfNecessary(dto, boolQueryBuilder);

        // 关键字搜索
        keywordSearch(dto, boolQueryBuilder);

        // 进行聚合分析
        agg(dto, builder, isAgg);

        // 排序
        sort(dto, builder, boolQueryBuilder);

        //分页
        if (dto.getPageNumber() > 0) {
            builder.from((dto.getPageNumber() - 1) * dto.getPageSize());
            builder.size(dto.getPageSize());
        }

        //商品表
        builder.index(EsIndexEnum.GOODS.value());

        SearchRequest build = builder.build();
        log.debug("构建的DSL语句 {}", build.toString());
        return build;
    }

    /**
     * 聚合分析
     */
    private void agg(GoodsSearchDTO dto, SearchRequest.Builder builder, Boolean isAgg) {
        Map<String, Aggregation> map = Maps.newHashMap();
        // 店铺进行聚合
        if (dto.getKeyword() != null && dto.getKeyword().length() > 1) {
            // 按照店铺进行聚合
            Aggregation shopIdAggregation = AggregationBuilders.terms(t -> t.field(EsConstant.SHOP_ID).size(1));
            map.put(EsConstant.SHOP, shopIdAggregation);
        }

        if (Objects.isNull(isAgg) || !isAgg) {
            return;
        }

        // 按照品牌进行聚合
        Aggregation brandIdAggregation = AggregationBuilders.terms(t -> t.field(EsConstant.BRAND_ID).size(10));
        Aggregation brandNameAggregation = AggregationBuilders.terms(t -> t.field(EsConstant.BRAND_NAME).size(1));
        brandIdAggregation.aggregations().put(EsConstant.BRAND_NAME, brandNameAggregation);
        Aggregation brandImgAggregation = AggregationBuilders.terms(t -> t.field(EsConstant.BRAND_IMG).size(1));
        brandIdAggregation.aggregations().put(EsConstant.BRAND_IMG, brandImgAggregation);
        map.put(EsConstant.BRANDS, brandIdAggregation);

        // 搜索平台商品，按照平台分类信息进行聚合
        if (Objects.isNull(dto.getShopId())) {
            Aggregation categoryIdAggregation =
                    AggregationBuilders.terms(t -> t.field(EsConstant.CATEGORY_ID).size(10));
            Aggregation categoryNameAggregation =
                    AggregationBuilders.terms(t -> t.field(EsConstant.CATEGORY_NAME).size(1));
            categoryIdAggregation.aggregations().put(EsConstant.CATEGORY_NAME, categoryNameAggregation);
            map.put(EsConstant.CATEGORIES, categoryIdAggregation);
        }
        // 搜索店铺中的商品，按照店铺分类信息进行聚合
        else {
            Aggregation secondaryCategoryIdAggregation =
                    AggregationBuilders.terms(t -> t.field(EsConstant.SHOP_CATEGORY_ID).size(10));
            Aggregation secondaryCategoryNameAggregation =
                    AggregationBuilders.terms(t -> t.field(EsConstant.SHOP_CATEGORY_NAME).size(1));
            secondaryCategoryIdAggregation.aggregations().put(EsConstant.SHOP_CATEGORY_NAME,
                    secondaryCategoryNameAggregation);
            map.put(EsConstant.SHOP_CATEGORIES, secondaryCategoryIdAggregation);
        }

        // 按照属性信息进行聚合
        Aggregation attrsAggregation = AggregationBuilders.nested(n -> n.path(EsConstant.ATTRS).name(EsConstant.ATTRS));

        // 按照属性ID进行聚合
        Aggregation attrIdAggregation = AggregationBuilders.terms(t -> t.field(EsConstant.ATTR_ATTR_ID).size(10));
        attrsAggregation.aggregations().put(EsConstant.ATTR_IDS, attrIdAggregation);
        // 按照属性VALUE进行聚合
        Aggregation attrValueIdAggregation =
                AggregationBuilders.terms(t -> t.field(EsConstant.ATTR_ATTR_VALUE_ID).size(10));
        attrIdAggregation.aggregations().put(EsConstant.ATTR_VALUE_IDS, attrValueIdAggregation);

        List<String> includesList = Lists.newArrayList(EsConstant.ATTR_ATTR_NAME, EsConstant.ATTR_ATTR_VALUE_ID,
                EsConstant.ATTR_ATTR_VALUE_NAME);
        Aggregation topHitsAggregation =
                AggregationBuilders.topHits(t -> t.source(s -> s.filter(f -> f.includes(includesList))).sort(s -> s.field(FieldSort.of(f -> f.field(EsConstant.ATTR_ATTR_VALUE_NAME).order(SortOrder.Asc)))).size(1));
        attrValueIdAggregation.aggregations().put(EsConstant.TOP_HITS_DATA, topHitsAggregation);

        builder.aggregations(map);
    }

    /**
     * 关键字搜索
     */
    private void keywordSearch(GoodsSearchDTO dto, BoolQuery.Builder boolQueryBuilder) {
        String keyword = dto.getKeyword();
        if (StrUtil.isBlank(keyword)) {
            return;
        }
        // 创建查询语句 ES中must和should不能同时使用 同时使用should失效 嵌套多个must 将should条件拼接在一个must中即可
        BoolQuery.Builder builder = new BoolQuery.Builder();
        builder.should(s -> s.match(t -> t.field(EsConstant.SPU_NAME).query(keyword).boost(Float.valueOf("6"))));
        if (keyword.length() > 1) {
            // 卖点，不分词
            builder.should(s -> s.matchPhrase(t -> t.field(EsConstant.SELLING_POINT).query(keyword).boost(Float.valueOf("3"))));
            builder.should(s -> s.matchPhrase(t -> t.field(EsConstant.SHOP_NAME).query(keyword)));
        }
        boolQueryBuilder.must(builder.build()._toQuery());
    }

    /**
     * 进行排序
     */
    private void sort(GoodsSearchDTO dto, SearchRequest.Builder builder, BoolQuery.Builder boolQueryBuilder) {
        //排序 如果排序规则设为空，则按照一定的算分规则进行排序，否则按照用户指定排序规则进行排序
        if (Objects.isNull(dto.getSort())) {
            List<FunctionScore> list = new ArrayList<>();
            FunctionScore saleNumFunctionScore =
                    FunctionScore.of(f -> f.fieldValueFactor(fv -> fv.field(EsConstant.SALE_NUM).modifier(FieldValueFactorModifier.Log1p).factor(Double.valueOf("0.1"))));
            list.add(saleNumFunctionScore);
            FunctionScoreQuery functionScoreQuery =
                    FunctionScoreQuery.of(f -> f.query(boolQueryBuilder.build()._toQuery()).functions(list).scoreMode(FunctionScoreMode.Sum).boostMode(FunctionBoostMode.Sum));
            builder.query(functionScoreQuery._toQuery());
        } else {
            List<SortOptions> list = new ArrayList<>();
            for (EsGoodsSortEnum enumValue : EsGoodsSortEnum.values()) {
                if (!Objects.equals(enumValue.value(), dto.getSort())) {
                    continue;
                }
                if (EsGoodsSortEnum.isAsc(enumValue)) {
                    SortOptions sortOptions =
                            SortOptions.of(s -> s.field(f -> f.field(enumValue.sort()).order(SortOrder.Asc)));
                    list.add(sortOptions);
                } else if (EsGoodsSortEnum.isDesc(enumValue)) {
                    SortOptions sortOptions =
                            SortOptions.of(s -> s.field(f -> f.field(enumValue.sort()).order(SortOrder.Desc)));
                    list.add(sortOptions);
                } else {

                }
            }
            builder.sort(list).query(boolQueryBuilder.build()._toQuery());
        }
    }

    /**
     * 过滤查询条件，如果有必要的话
     *
     * @dto dto 查询条件
     * @dto boolQuery 组合进boolQuery
     */
    private void filterQueryIfNecessary(GoodsSearchDTO dto, BoolQuery.Builder boolQueryBuilder) {
        // 店铺id
        Long shopId = dto.getShopId();
        if (Objects.nonNull(shopId)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.SHOP_ID).value(shopId)));
        }

        // spu状态
        Integer spuStatus = dto.getSpuStatus();
        Integer dataType = dto.getDataType();
        List<FieldValue> statusList = new ArrayList<>();
        if (Objects.nonNull(spuStatus)) {
            statusList.add(FieldValue.of(spuStatus));
        } else if (Objects.equals(dataType, DataType.SALE.value())) {
            statusList.add(FieldValue.of(StatusEnum.ENABLE.value()));
        } else if (Objects.equals(dataType, DataType.DISABLE.value())) {
            statusList.add(FieldValue.of(StatusEnum.DISABLE.value()));
        } else {
            statusList.add(FieldValue.of(StatusEnum.ENABLE.value()));
            statusList.add(FieldValue.of(StatusEnum.DISABLE.value()));
        }
        if (CollectionUtil.isNotEmpty(statusList)) {
            TermsQueryField termsQueryField = new TermsQueryField.Builder().value(statusList).build();
            BoolQuery boolQuery =
                    BoolQuery.of(b -> b.should(s -> s.terms(t -> t.field(EsConstant.SPU_STATUS).terms(termsQueryField))));
            boolQueryBuilder.filter(boolQuery._toQuery());
        }

        this.categoryFilterQuery(dto, boolQueryBuilder);

        // 是否有库存
        Integer hasStock = dto.getHasStock();
        if (Objects.nonNull(hasStock)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.HAS_STOCK).value(Objects.equals(hasStock, 1))));
        }

        // 商品类型
        Integer selfShop = dto.getSelfShop();
        if (Objects.nonNull(selfShop)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.SHOP_TYPE).value(selfShop)));
        }

        // 品牌
        String brandIds = dto.getBrandIds();
        if (StrUtil.isNotBlank(brandIds)) {
            String[] ids = brandIds.split(EsConstant.CONNECTION_SYMBOLS);
            List<FieldValue> brandIdList = new ArrayList<>();
            for (String brandId : ids) {
                brandIdList.add(FieldValue.of(brandId));
            }
            TermsQueryField termsQueryField = new TermsQueryField.Builder().value(brandIdList).build();
            BoolQuery boolQuery =
                    BoolQuery.of(b -> b.should(s -> s.terms(t -> t.field(EsConstant.BRAND_ID).terms(termsQueryField))));
            boolQueryBuilder.filter(boolQuery._toQuery());
        }

        // spuId列表
        List<Long> spuIds = dto.getSpuIds();
        if (CollectionUtil.isNotEmpty(spuIds)) {
            List<FieldValue> spuIdList = new ArrayList<>();
            for (Long spuId : spuIds) {
                spuIdList.add(FieldValue.of(spuId));
            }
            TermsQueryField termsQueryField = new TermsQueryField.Builder().value(spuIdList).build();
            boolQueryBuilder.filter(f -> f.terms(t -> t.field(EsConstant.SPU_ID).terms(termsQueryField)));
        }

        // 规格属性值
        String attrValueIds = dto.getAttrValueIds();
        if (StrUtil.isNotBlank(attrValueIds)) {
            String[] ids = attrValueIds.split(EsConstant.CONNECTION_SYMBOLS);
            List<FieldValue> attrValueIdList = new ArrayList<>();
            for (String attrValueId : ids) {
                attrValueIdList.add(FieldValue.of(attrValueId));
            }
            TermsQueryField termsQueryField = new TermsQueryField.Builder().value(attrValueIdList).build();
            BoolQuery boolQuery =
                    BoolQuery.of(b -> b.should(s -> s.terms(t -> t.field(EsConstant.ATTR_ATTR_VALUE_ID).terms(termsQueryField))));
            NestedQuery nestedQuery =
                    NestedQuery.of(n -> n.path(EsConstant.ATTRS).query(boolQuery._toQuery()).scoreMode(ChildScoreMode.None));
            boolQueryBuilder.filter(nestedQuery._toQuery());
        }

        // 价格区间
        Long minPrice = dto.getMinPrice();
        Long maxPrice = dto.getMaxPrice();
        if (minPrice != null || maxPrice != null) {
            RangeQuery.Builder builder = new RangeQuery.Builder().field(EsConstant.PRICE_FEE);
            if (minPrice != null) {
                builder.gte(JsonData.of(minPrice));
            }
            if (maxPrice != null) {
                builder.lte(JsonData.of(maxPrice));
            }
            boolQueryBuilder.filter(builder.build()._toQuery());
        }

        // 销量区间
        Long minSaleNum = dto.getMinSaleNum();
        Long maxSaleNum = dto.getMaxSaleNum();
        if (minSaleNum != null || maxSaleNum != null) {
            RangeQuery.Builder builder = new RangeQuery.Builder().field(EsConstant.SALE_NUM);
            if (minSaleNum != null) {
                builder.gte(JsonData.of(minSaleNum));
            }
            if (maxSaleNum != null) {
                builder.lte(JsonData.of(maxSaleNum));
            }
            boolQueryBuilder.filter(builder.build()._toQuery());
        }
    }

    private void categoryFilterQuery(GoodsSearchDTO dto, BoolQuery.Builder boolQueryBuilder) {
        // 商家一级分类
        Long shopPrimaryCategoryId = dto.getShopPrimaryCategoryId();
        if (Objects.nonNull(shopPrimaryCategoryId)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.SHOP_PRIMARY_CATEGORY_ID).value(shopPrimaryCategoryId)));
        }

        //商家二级分类
        Long shopSecondaryCategoryId = dto.getShopSecondaryCategoryId();
        if (Objects.nonNull(shopSecondaryCategoryId)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.SHOP_SECONDARY_CATEGORY_ID).value(shopSecondaryCategoryId)));
        }

        // 平台一级分类
        Long primaryCategoryId = dto.getPrimaryCategoryId();
        if (Objects.nonNull(primaryCategoryId)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.PRIMARY_CATEGORY_ID).value(primaryCategoryId)));
        }

        // 平台三级分类
        Long categoryId = dto.getCategoryId();
        if (Objects.nonNull(categoryId)) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsConstant.CATEGORY_ID).value(categoryId)));
        }
    }

    /**
     * 根据店铺id列表获取每个店铺的spu列表
     *
     * @return
     * @dto shopIds
     */
    public List<EsSpuVO> limitSizeListByShopIds(List<Long> shopIds, Integer size) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        // 构建bool-query
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
        // 过滤
        List<FieldValue> shopIdList = new ArrayList<>();
        for (Long shopId : shopIds) {
            shopIdList.add(FieldValue.of(shopId));
        }
        TermsQueryField termsQueryField = new TermsQueryField.Builder().value(shopIdList).build();
        boolQueryBuilder.filter(f -> f.terms(t -> t.field(EsConstant.SHOP_ID).terms(termsQueryField)));
        builder.query(boolQueryBuilder.build()._toQuery());
        // 聚合分析
        Aggregation shopIdAggregation = AggregationBuilders.terms(t -> t.field(EsConstant.SHOP_ID));
        List<String> includesList = Lists.newArrayList(EsConstant.SPU_NAME, EsConstant.MAIN_IMG_URL,
                EsConstant.SHOP_ID, EsConstant.SPU_ID, EsConstant.PRICE_FEE);
        Aggregation topHitsAggregation =
                AggregationBuilders.topHits(t -> t.source(s -> s.filter(f -> f.includes(includesList))).sort(s -> s.field(FieldSort.of(f -> f.field(EsConstant.SALE_NUM).order(SortOrder.Desc)))).size(size));
        shopIdAggregation.aggregations().put(EsConstant.TOP_HITS_DATA, topHitsAggregation);

        builder.aggregations(EsConstant.SHOP_COUPON, shopIdAggregation).size(0);

        SearchRequest searchRequest = builder.index(EsIndexEnum.GOODS.value()).build();
        log.debug("构建的DSL语句 {}", searchRequest.toString());

        //2、执行检索请求
        List<EsSpuVO> spuList = Lists.newArrayList();
        try {
            SearchResponse<EsGoodsSearchVO> searchResponse = client.search(searchRequest, EsGoodsSearchVO.class);
            log.debug("搜索返回结果：" + searchResponse.toString());

            List<Hit<EsGoodsSearchVO>> hitList = searchResponse.hits().hits();
            for (Hit<EsGoodsSearchVO> hit : hitList) {
                EsGoodsSearchVO vo = hit.source();
                if (vo == null || CollectionUtil.isEmpty(vo.getSpus())) {
                    break;
                }
                spuList.addAll(vo.getSpus());
            }
        } catch (IOException e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
        }
        return spuList;
    }

//    /**
//     * 从聚合数据中获取商品列表
//     *
//     * @return
//     * @dto response
//     */
//    private List<EsSpuVO> loadSpuListByAggregations(SearchResponse response) {
//        List<EsSpuVO> spuList = new ArrayList<>();
//        Aggregations aggregations = response.getAggregations();
//        ParsedLongTerms shopCouponTerm = aggregations.get(EsConstant.SHOP_COUPON);
//        if (Objects.nonNull(shopCouponTerm)) {
//            List<? extends Terms.Bucket> buckets = shopCouponTerm.getBuckets();
//            for (Terms.Bucket bucket : buckets) {
//                Aggregations shopAggs = bucket.getAggregations();
//                ParsedTopHits shopHits = shopAggs.get(EsConstant.TOP_HITS_DATA);
//                spuList.addAll(getSpuListByResponse(shopHits.getHits().getHits()));
//            }
//        }
//        return spuList;
//    }

    /**
     * 商品管理分页搜索es数据的信息
     *
     * @return 搜索结果
     * @dto dto 分页数据
     * @dto dto 商品搜索条件
     */
    public EsPageVO<EsSpuVO> adminPage(GoodsSearchDTO dto) {
        loadSpuStatus(dto);
        EsPageVO<EsSpuVO> esPageVO = new EsPageVO<>();
        SearchResponse<EsGoodsSearchVO> searchResponse = pageSearchResult(dto, Boolean.FALSE);
        // 商品信息
        esPageVO.setRecords(buildSpuAdminList(searchResponse));
        // 分页信息
        buildSearchPage(dto, esPageVO, searchResponse);
        return esPageVO;
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
    public List<EsSpuVO> buildSpuAdminList(SearchResponse<EsGoodsSearchVO> searchResponse) {
        List<EsSpuVO> spuList = Lists.newArrayList();
        List<Hit<EsGoodsSearchVO>> hitList = searchResponse.hits().hits();
        for (Hit<EsGoodsSearchVO> hit : hitList) {
            EsGoodsSearchVO vo = hit.source();
            if (vo == null || CollectionUtil.isEmpty(vo.getSpus())) {
                break;
            }
            spuList.addAll(vo.getSpus());
        }
        return spuList;
    }

//    /**
//     * 处理聚合国际化信息
//     *
//     * @return 对应语言的字段
//     * @dto json 数据
//     * @dto field 字段
//     */
//    private String handleAggregationsLang(String json, String field, String defaultField) {
//        Map<String, Object> map = JsonUtil.fromJson(json, new TypeReference<Map<String, Object>>() {
//        });
//        Object object;
//        // 找不到指定语言的数据，就查默认语言
//        if (Objects.isNull(map.get(field))) {
//            object = map.get(defaultField);
//        }
//        // 获取指定语言的数据
//        else {
//            object = map.get(field);
//        }
//        // 没有查到数据
//        if (Objects.isNull(object)) {
//            return null;
//        }
//        return object.toString();
//    }

    /**
     * 构建分页数据
     *
     * @dto dto
     * @dto esPageVO
     * @dto response
     */
    private void buildSearchPage(GoodsSearchDTO dto, EsPageVO<?> esPageVO,
                                 SearchResponse<EsGoodsSearchVO> searchResponse) {
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
