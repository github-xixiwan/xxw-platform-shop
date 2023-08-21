package com.xxw.shop.manager;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xxw.shop.api.search.dto.GoodsSearchDTO;
import com.xxw.shop.api.search.vo.*;
import com.xxw.shop.constant.*;
import com.xxw.shop.module.common.bo.EsGoodsBO;
import com.xxw.shop.module.common.constant.StatusEnum;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        SearchResponse<EsGoodsBO> searchResponse = pageSearchResult(dto, Boolean.TRUE);
        if (searchResponse == null) {
            return new EsPageVO<>();
        }
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
        SearchResponse<EsGoodsBO> searchResponse = pageSearchResult(dto, Boolean.TRUE);
        if (searchResponse == null) {
            return new EsPageVO<>();
        }
        return buildSearchResult(dto, searchResponse);
    }

    /**
     * 通过搜索信息分页搜索es数据的信息
     *
     * @return 搜索结果
     * @dto dto 分页数据
     * @dto dto 商品搜索条件
     * @dto isAgg true:聚合搜索  false:非聚合搜索  null:非聚合搜索
     */
    private SearchResponse<EsGoodsBO> pageSearchResult(GoodsSearchDTO dto, Boolean isAgg) {
        //1、准备检索请求
        SearchRequest searchRequest = buildSearchRequest(dto, isAgg);
        SearchResponse<EsGoodsBO> searchResponse = null;
        try {
            //2、执行检索请求
            searchResponse = client.search(searchRequest, EsGoodsBO.class);
            log.debug("搜索返回结果：" + searchResponse.toString());
        } catch (Exception e) {
            log.error("elasticsearch异常 错误：{}", ExceptionUtils.getStackTrace(e));
        }
        return searchResponse;
    }

    /**
     * 构建结果数据
     */
    private EsPageVO<EsGoodsSearchVO> buildSearchResult(GoodsSearchDTO dto, SearchResponse<EsGoodsBO> searchResponse) {
        EsPageVO<EsGoodsSearchVO> esPageVO = new EsPageVO<>();

        //1、返回的所有查询到的商品
        List<EsGoodsSearchVO> list = getEsGoodsSearchVOList(searchResponse);
        esPageVO.setRecords(list);

        // 分页信息
        buildSearchPage(dto, esPageVO, searchResponse);
        return esPageVO;
    }

    private List<EsGoodsSearchVO> getEsGoodsSearchVOList(SearchResponse<EsGoodsBO> searchResponse) {
        EsGoodsSearchVO esGoodsSearchVO = new EsGoodsSearchVO();
        //===============spu列表信息====================//
        List<EsGoodsBO> spus = Lists.newArrayList();
        List<Hit<EsGoodsBO>> hitList = searchResponse.hits().hits();
        for (Hit<EsGoodsBO> hit : hitList) {
            EsGoodsBO vo = hit.source();
            spus.add(vo);
        }
        esGoodsSearchVO.setSpus(spus);

        //===============聚合信息====================//
        Map<String, Aggregate> aggregations = searchResponse.aggregations();
        if (CollectionUtil.isNotEmpty(aggregations)) {
            loadAggregationsData(esGoodsSearchVO, aggregations);
        }
        List<EsGoodsSearchVO> list = Lists.newArrayList();
        list.add(esGoodsSearchVO);
        return list;
    }

    private void loadAggregationsData(EsGoodsSearchVO esGoodsSearchVO, Map<String, Aggregate> aggregations) {
        //===============品牌信息====================//
        Aggregate brandsAggregate = aggregations.get(EsConstant.BRANDS);
        if (brandsAggregate != null) {
            List<BrandSearchVO> list = Lists.newArrayList();
            brandsAggregate.lterms().buckets().array().forEach(a -> {
                BrandSearchVO brandSearchVO = new BrandSearchVO();
                brandSearchVO.setBrandId(a.key());
                Map<String, Aggregate> brandIdAggregations = a.aggregations();
                Aggregate brandNameAggregate = brandIdAggregations.get(EsConstant.BRAND_NAME);
                if (brandNameAggregate != null) {
                    brandNameAggregate.sterms().buckets().array().forEach(aa -> {
                        brandSearchVO.setBrandName(aa.key().stringValue());
                    });
                }
                Aggregate brandImgAggregate = brandIdAggregations.get(EsConstant.BRAND_IMG);
                if (brandImgAggregate != null) {
                    brandImgAggregate.sterms().buckets().array().forEach(aa -> {
                        brandSearchVO.setBrandImg(aa.key().stringValue());
                    });
                }
                list.add(brandSearchVO);
            });
            esGoodsSearchVO.setBrands(list);
        }

        //===============分类信息====================//
        String categoryName = null;
        Aggregate categoriesAggregate = null;
        // 平台分类
        if (Objects.nonNull(aggregations.get(EsConstant.CATEGORIES))) {
            categoryName = EsConstant.CATEGORY_NAME;
            categoriesAggregate = aggregations.get(EsConstant.CATEGORIES);
        }
        // 店铺分类
        else {
            categoryName = EsConstant.SHOP_CATEGORY_NAME;
            categoriesAggregate = aggregations.get(EsConstant.SHOP_CATEGORIES);
        }
        if (categoriesAggregate != null) {
            List<CategorySearchVO> list = Lists.newArrayList();
            String finalCategoryName = categoryName;
            categoriesAggregate.lterms().buckets().array().forEach(a -> {
                CategorySearchVO categorySearchVO = new CategorySearchVO();
                categorySearchVO.setCategoryId(a.key());
                Map<String, Aggregate> brandIdAggregations = a.aggregations();
                Aggregate categoryNameAggregate = brandIdAggregations.get(finalCategoryName);
                if (categoryNameAggregate != null) {
                    categoryNameAggregate.sterms().buckets().array().forEach(aa -> {
                        categorySearchVO.setName(aa.key().stringValue());
                    });
                }
                list.add(categorySearchVO);
            });
            esGoodsSearchVO.setCategorys(list);
        }

        //===============店铺信息====================//
        Aggregate shopAggregate = aggregations.get(EsConstant.SHOP);
        if (shopAggregate != null) {
            ShopInfoSearchVO shopInfo = new ShopInfoSearchVO();
            shopAggregate.lterms().buckets().array().forEach(a -> {
                shopInfo.setShopId(a.key());
            });
            esGoodsSearchVO.setShopInfo(shopInfo);
        }

        //===============属性信息====================//
        Aggregate attrsAggregate = aggregations.get(EsConstant.ATTRS);
        if (attrsAggregate != null) {
            Aggregate attrIdsAggregate = attrsAggregate.nested().aggregations().get(EsConstant.ATTR_IDS);
            if (attrIdsAggregate != null) {
                List<AttrSearchVO> list = Lists.newArrayList();
                attrIdsAggregate.lterms().buckets().array().forEach(a -> {
                    AttrSearchVO attrSearchVO = new AttrSearchVO();
                    attrSearchVO.setAttrId(a.key());
                    Aggregate attrValueIdsAggregate = a.aggregations().get(EsConstant.ATTR_VALUE_IDS);
                    if (attrValueIdsAggregate != null) {
                        attrValueIdsAggregate.lterms().buckets().array().forEach(aa -> {
                            aa.aggregations().get(EsConstant.TOP_HITS_DATA).topHits().hits().hits().forEach(h -> {
                                JsonData source = h.source();
                                if (source != null) {
                                    JSONObject jsonObject = source.to(JSONObject.class);
                                    if (jsonObject != null) {
                                        attrSearchVO.setAttrName(String.valueOf(jsonObject.get("attrName")));

                                        AttrValueSearchVO vo = new AttrValueSearchVO();
                                        vo.setAttrValueId(Long.valueOf(String.valueOf(jsonObject.get("attrValueId"))));
                                        vo.setAttrValueName(String.valueOf(jsonObject.get("attrValueName")));
                                        attrSearchVO.setAttrValues(Lists.newArrayList(vo));
                                    }
                                }
                                list.add(attrSearchVO);
                            });
                        });
                    }
                });
                esGoodsSearchVO.setAttrs(list);
            }
        }
    }

    /**
     * 准备检索请求
     *
     * @return
     * @dto dto 搜索参数
     * @dto isAgg true:聚合搜索  false:非聚合搜索  null:非聚合搜索
     */
    private SearchRequest buildSearchRequest(GoodsSearchDTO dto, Boolean isAgg) {
        SearchRequest.Builder builder = new SearchRequest.Builder();

        // 构建bool-query
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
        Map<String, Aggregation> subMap = Maps.newHashMap();
        // 店铺进行聚合
        if (dto.getKeyword() != null && dto.getKeyword().length() > 1) {
            // 按照店铺进行聚合
            Aggregation shopIdAggregation = Aggregation.of(a -> a.terms(t -> t.field(EsConstant.SHOP_ID).size(1)));
            map.put(EsConstant.SHOP, shopIdAggregation);
        }
        if (Objects.isNull(isAgg) || !isAgg) {
            return;
        }

        // 按照品牌进行聚合
        Aggregation brandIdAggregation =
                Aggregation.of(a -> a.terms(t -> t.field(EsConstant.BRAND_ID).size(10)).aggregations(EsConstant.BRAND_NAME, aa -> aa.terms(t -> t.field(EsConstant.BRAND_NAME).size(1))).aggregations(EsConstant.BRAND_IMG, aa -> aa.terms(t -> t.field(EsConstant.BRAND_IMG).size(1))));
        map.put(EsConstant.BRANDS, brandIdAggregation);

        // 搜索平台商品，按照平台分类信息进行聚合
        if (Objects.isNull(dto.getShopId())) {
            Aggregation categoryIdAggregation =
                    Aggregation.of(a -> a.terms(t -> t.field(EsConstant.CATEGORY_ID).size(10)).aggregations(EsConstant.CATEGORY_NAME, aa -> aa.terms(t -> t.field(EsConstant.CATEGORY_NAME).size(1))));
            map.put(EsConstant.CATEGORIES, categoryIdAggregation);
        }

        // 搜索店铺中的商品，按照店铺分类信息进行聚合
        else {
            Aggregation secondaryCategoryIdAggregation =
                    Aggregation.of(a -> a.terms(t -> t.field(EsConstant.SHOP_CATEGORY_ID).size(10)).aggregations(EsConstant.SHOP_CATEGORY_NAME, aa -> aa.terms(t -> t.field(EsConstant.SHOP_CATEGORY_NAME).size(1))));
            map.put(EsConstant.SHOP_CATEGORIES, secondaryCategoryIdAggregation);
        }

        // 按照属性信息进行聚合
        List<String> includesList = Lists.newArrayList(EsConstant.ATTR_ATTR_NAME, EsConstant.ATTR_ATTR_VALUE_ID,
                EsConstant.ATTR_ATTR_VALUE_NAME);
        Aggregation attrsAggregation = Aggregation.of(a -> a.nested(n -> n.path(EsConstant.ATTRS))
                // 按照属性ID进行聚合
                .aggregations(EsConstant.ATTR_IDS, aa -> aa.terms(t -> t.field(EsConstant.ATTR_ATTR_ID).size(10))
                        // 按照属性VALUE进行聚合
                        .aggregations(EsConstant.ATTR_VALUE_IDS,
                                aaa -> aaa.terms(t -> t.field(EsConstant.ATTR_ATTR_VALUE_ID).size(10)).aggregations(EsConstant.TOP_HITS_DATA, aaaa -> aaaa.topHits(th -> th.source(s -> s.filter(f -> f.includes(includesList))).sort(s -> s.field(FieldSort.of(f -> f.field(EsConstant.ATTR_ATTR_VALUE_NAME).order(SortOrder.Asc)))).size(1))))));
        map.put(EsConstant.ATTRS, attrsAggregation);

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
     * 商品管理分页搜索es数据的信息
     *
     * @return 搜索结果
     * @dto dto 分页数据
     * @dto dto 商品搜索条件
     */
    public EsPageVO<EsGoodsBO> adminPage(GoodsSearchDTO dto) {
        loadSpuStatus(dto);
        EsPageVO<EsGoodsBO> esPageVO = new EsPageVO<>();
        SearchResponse<EsGoodsBO> searchResponse = pageSearchResult(dto, Boolean.FALSE);
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
    public List<EsGoodsBO> buildSpuAdminList(SearchResponse<EsGoodsBO> searchResponse) {
        List<EsGoodsBO> spuList = Lists.newArrayList();
        List<Hit<EsGoodsBO>> hitList = searchResponse.hits().hits();
        for (Hit<EsGoodsBO> hit : hitList) {
            EsGoodsBO vo = hit.source();
            spuList.add(vo);
        }
        return spuList;
    }

    /**
     * 构建分页数据
     *
     * @dto dto
     * @dto esPageVO
     * @dto response
     */
    private void buildSearchPage(GoodsSearchDTO dto, EsPageVO<?> esPageVO, SearchResponse<EsGoodsBO> searchResponse) {
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
