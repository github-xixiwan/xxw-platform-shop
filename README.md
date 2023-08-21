## xxw-shop
一个基于Spring Cloud、Nacos、Seata、Sentinel、Mysql、Redis、RocketMQ、Canal、ElasticSearch、Minio的微服务B2B2C电商商城系统，采用主流的互联网技术架构、全新的UI
设计、支持集群部署、服务注册和发现以及拥有完整的订单流程等，代码完全开源，没有任何二次封装，是一个非常适合二次开发的电商平台系统。

## 前言
本商城致力于为中大型企业打造一个功能完整、易于维护的微服务B2B2C电商商城系统，采用主流微服务技术实现。后台管理系统包含平台管理，店铺管理、商品管理、订单管理、规格管理、权限管理、资源管理等模块。

## 项目链接
JAVA后台：https://github.com/github-xixiwan/xxw-shop

平台端：https://github.com/github-xixiwan/xxw-shop-platform

商家端：https://github.com/github-xixiwan/xxw-shop-business

用户端：https://github.com/github-xixiwan/xxw-shop-consumer

## 目录结构
```
xxw-shop
├─xxw-shop-applications  --应用
│ ├─xxw-shop-api  --feign接口定义
│ │ ├─xxw-shop-api-auth  --授权相关feign
│ │ ├─xxw-shop-api-business  --商家端相关feign
│ │ ├─xxw-shop-api-goods  --商品相关feign
│ │ ├─xxw-shop-api-order  --订单相关feign
│ │ ├─xxw-shop-api-platform  --平台端相关feign
│ │ ├─xxw-shop-api-rbac  --权限相关feign
│ │ ├─xxw-shop-api-search  --搜索相关feign
│ │ └─xxw-shop-api-user  --用户相关feign
│ ├─xxw-shop-gateway  --网关
│ └─xxw-shop-service  --服务
│ │ ├─xxw-shop-service-auth  --授权服务
│ │ ├─xxw-shop-service-business  --商家端服务
│ │ ├─xxw-shop-service-goods  --商品服务
│ │ ├─xxw-shop-service-order  --订单服务
│ │ ├─xxw-shop-service-payment  --支付服务
│ │ ├─xxw-shop-service-platform  --平台端服务
│ │ ├─xxw-shop-service-rbac  --权限服务
│ │ ├─xxw-shop-service-search  --搜索服务
│ │ ├─xxw-shop-service-support  --公共支撑服务
│ │ └─xxw-shop-service-user  --用户服务
├─xxw-shop-dependencies  --版本管理
├─xxw-shop-modules  --基础模块
│ ├─xxw-shop-module-cache  --缓存模块
│ ├─xxw-shop-module-common  --公共模块
│ ├─xxw-shop-module-mybatis  --数据库模块
│ ├─xxw-shop-module-nacos  --注册中心与配置中心模块
│ ├─xxw-shop-module-security  --鉴权模块
│ └─xxw-shop-module-web  --容器模块
└─xxw-shop-starters  --配置
  ├─xxw-shop-starter-canal  --数据同步
  ├─xxw-shop-starter-captcha  --验证码
  ├─xxw-shop-starter-elasticsearch  --搜索
  └─xxw-shop-starter-minio  --存储
```

## 技术选型
|                    组件名                     |    版本号     |
|:------------------------------------------:|:----------:|
|             spring-cloud-build             |   4.0.5    |
|          spring-boot-dependencies          |   3.0.9    |
|         spring-cloud-dependencies          |  2022.0.4  |
|     spring-cloud-alibaba-dependencies      | 2022.0.0.0 |
|      mybatis-flex-spring-boot-starter      |   1.5.6    |
|        mybatis-spring-boot-starter         |   3.0.2    |
|knife4j-openapi3-jakarta-spring-boot-starter|   4.1.0    |
|        spring-boot-starter-captcha         |   1.3.0    |
|                 hutool-all                 |   5.8.21   |
|                   minio                    |   8.5.4    |
|               elasticsearch                |   8.8.2    |

## elasticsearch

- goods

```
PUT goods
{
  "mappings" : {
    "properties" : {
      "attrs" : {
        "type" : "nested",
        "properties" : {
          "attrId" : {
            "type" : "long"
          },
          "attrName" : {
            "type" : "keyword"
          },
          "attrValueId" : {
            "type" : "long"
          },
          "attrValueName" : {
            "type" : "keyword"
          }
        }
      },
      "tags" : {
        "type" : "nested",
        "properties" : {
          "tagId" : {
            "type" : "long"
          },
          "seq" : {
            "type" : "integer"
          }
        }
      },
      "brandId" : {
        "type" : "long"
      },
      "brandImg" : {
        "type" : "keyword"
      },
      "brandName" : {
        "type" : "keyword"
      },
      "code" : {
        "type" : "text",
        "fields" : {
          "keyword" : {
            "type" : "keyword",
            "ignore_above" : 256
          }
        }
      },
      "commentNum" : {
        "type" : "integer"
      },
      "hasStock" : {
        "type" : "boolean"
      },
      "imgUrls" : {
        "type" : "keyword",
        "index" : false,
        "doc_values" : false
      },
      "mainImgUrl" : {
        "type" : "text",
        "fields" : {
          "keyword" : {
            "type" : "keyword",
            "ignore_above" : 256
          }
        }
      },
      "marketPriceFee" : {
        "type" : "long"
      },
      "priceFee" : {
        "type" : "long"
      },
      "saleNum" : {
        "type" : "integer"
      },
      "sellingPoint" : {
        "type" : "text",
        "analyzer" : "ik_max_word",
        "search_analyzer" : "ik_smart"
      },
      "shopId" : {
        "type" : "long"
      },
      "shopImg" : {
        "type" : "keyword",
        "index" : false,
        "doc_values" : false
      },
      "shopName" : {
        "type" : "text",
        "analyzer" : "ik_max_word",
        "search_analyzer" : "ik_smart"
      },
      "shopType" : {
        "type" : "integer"
      },
      "shopPrimaryCategoryId" : {
        "type" : "long"
      },
      "shopPrimaryCategoryName" : {
        "type" : "keyword"
      },
      "shopSecondaryCategoryId" : {
        "type" : "long"
      },
      "shopSecondaryCategoryName" : {
        "type" : "keyword"
      },
      "primaryCategoryId" : {
        "type" : "long"
      },
      "primaryCategoryName" : {
        "type" : "keyword"
      },
      "secondaryCategoryId" : {
        "type" : "long"
      },
      "secondaryCategoryName" : {
        "type" : "keyword"
      },
      "categoryId" : {
        "type" : "long"
      },
      "categoryName" : {
        "type" : "keyword"
      },
      "spuId" : {
        "type" : "long"
      },
      "spuName" : {
        "type" : "text",
        "analyzer" : "ik_max_word",
        "search_analyzer" : "ik_smart"
      },
      "spuStatus" : {
        "type" : "integer"
      },
      "success" : {
        "type" : "boolean"
      }
    }
  }
}
```

- order

```
PUT order
{
  "mappings": {
    "properties": {
      "orderId": {
        "type": "long"
      },
      "shopName": {
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart"
      },
      "shopId": {
        "type": "long"
      },
      "userId": {
        "type": "long"
      },
      "consignee": {
        "type": "text"
      },
      "mobile": {
        "type": "text"
      },
      "status": {
        "type": "integer"
      },
      "deliveryType": {
        "type": "integer"
      },
      "total": {
        "type": "long"
      },
      "closeType": {
        "type": "integer"
      },
      "orderAddrId": {
        "type": "long"
      },
      "isPayed": {
        "type": "integer"
      },
      "deleteStatus": {
        "type": "integer"
      },
      "orderItems": {
        "type": "nested",
        "properties": {
          "pic": {
            "type": "keyword",
            "index": false,
            "doc_values": false
          },
          "spuName": {
            "type": "text",
            "analyzer": "ik_max_word",
            "search_analyzer": "ik_smart"
          },
          "skuName": {
            "type": "keyword",
            "index": false,
            "doc_values": false
          },
          "count": {
            "type": "integer"
          },
          "price": {
            "type": "long"
          },
          "skuId": {
            "type": "long"
          },
          "orderItemId": {
            "type": "long"
          },
          "spuId": {
            "type": "long"
          },
          "shopId": {
            "type": "long"
          },
          "userId": {
            "type": "long"
          },
          "deliveryType": {
            "type": "integer"
          },
          "spuTotalAmount": {
            "type": "long"
          }
        }
      }
    }
  }
}
```