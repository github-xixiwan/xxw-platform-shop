package com.xxw.shop.starter.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.xxw.shop.module.common.exception.ElasticsearchException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public abstract class BaseElasticsearchService {

    @Resource
    protected ElasticsearchClient client;

    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public boolean hasIndex(String indexName) throws IOException {
        BooleanResponse exists = client.indices().exists(d -> d.index(indexName));
        return exists.value();
    }

    /**
     * 删除索引
     *
     * @param indexName
     * @throws IOException
     */
    public boolean deleteIndex(String indexName) throws IOException {
        DeleteIndexResponse response = client.indices().delete(d -> d.index(indexName));
        return true;
    }

    /**
     * 创建索引
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public boolean createIndex(String indexName) {
        try {
            CreateIndexResponse indexResponse = client.indices().create(c -> c.index(indexName));
        } catch (IOException e) {
            log.error("索引创建失败：{}", e.getMessage());
            throw new ElasticsearchException("创建索引失败");
        }
        return true;
    }

    /**
     * 创建索引，不允许外部直接调用
     *
     * @param indexName
     * @param mapping
     * @throws IOException
     */
    private boolean createIndex(String indexName, Map<String, Property> mapping) throws IOException {
        CreateIndexResponse createIndexResponse = client.indices().create(c -> {
            c.index(indexName).mappings(mappings -> mappings.properties(mapping));
            return c;
        });
        return createIndexResponse.acknowledged();
    }

    /**
     * 重新创建索引,如果已存在先删除
     *
     * @param indexName
     * @param mapping
     */
    public void reCreateIndex(String indexName, Map<String, Property> mapping) {
        try {
            if (this.hasIndex(indexName)) {
                this.deleteIndex(indexName);
            }
        } catch (IOException e) {
            log.error("删除索引失败：{}", e.getMessage());
            throw new ElasticsearchException("删除索引失败");
        }

        try {
            this.createIndex(indexName, mapping);
        } catch (IOException e) {
            log.error("重新创建索引失败：{}", e.getMessage());
            throw new ElasticsearchException("重新创建索引失败");
        }
    }

    /**
     * 新增数据
     *
     * @param indexName
     * @throws IOException
     */
    public boolean insertDocument(String indexName, Object obj, String id) {
        try {
            IndexResponse indexResponse = client.index(i -> i.index(indexName).id(id).document(obj));
            return true;
        } catch (IOException e) {
            log.error("新增ES数据失败：{}", e.getMessage());
            throw new ElasticsearchException("新增ES数据失败");
        }
    }

    /**
     * 删除数据
     *
     * @param indexName
     * @param id
     * @return
     */
    public boolean deleteDocument(String indexName, String id) {
        try {
            DeleteResponse deleteResponse = client.delete(d -> d.index(indexName).id(id));
        } catch (IOException e) {
            log.error("删除Es数据异常：{}", e.getMessage());
            throw new ElasticsearchException("删除Es数据异常");
        }
        return true;
    }

}
