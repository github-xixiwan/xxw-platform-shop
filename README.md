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