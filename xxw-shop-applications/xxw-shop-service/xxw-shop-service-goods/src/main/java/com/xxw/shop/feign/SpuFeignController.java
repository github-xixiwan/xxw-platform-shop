package com.xxw.shop.feign;

import com.xxw.shop.api.goods.feign.SpuFeignClient;
import com.xxw.shop.api.goods.vo.SkuVO;
import com.xxw.shop.api.goods.vo.SpuAndSkuVO;
import com.xxw.shop.api.goods.vo.SpuVO;
import com.xxw.shop.module.common.constant.StatusEnum;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.SkuService;
import com.xxw.shop.service.SpuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class SpuFeignController implements SpuFeignClient {

    @Resource
    private SpuService spuService;

    @Resource
    private SkuService skuService;

    @Override
    public ServerResponseEntity<SpuVO> getById(Long spuId) {
        SpuVO spuVO = spuService.getBySpuId(spuId);
        SpuVO spu = new SpuVO();
        spu.setSpuId(spuVO.getSpuId());
        spu.setName(spuVO.getName());
        spu.setMainImgUrl(spuVO.getMainImgUrl());
        return ServerResponseEntity.success(spu);
    }

    @Override
    public ServerResponseEntity<SpuAndSkuVO> getSpuAndSkuById(Long spuId, Long skuId) {
        SpuVO spu = spuService.getBySpuId(spuId);
        SkuVO sku = skuService.getSkuBySkuId(skuId);

        // 当商品状态不正常时，不能添加到购物车
        boolean spuIsNotExist = Objects.isNull(spu) || Objects.isNull(sku) || !Objects.equals(spu.getStatus(),
                StatusEnum.ENABLE.value()) || !Objects.equals(sku.getStatus(), StatusEnum.ENABLE.value()) || !Objects.equals(sku.getSpuId(), spu.getSpuId());
        if (spuIsNotExist) {
            // 当返回商品不存在时，前端应该将商品从购物车界面移除
            throw new BusinessException(SystemErrorEnumError.SPU_NOT_EXIST);
        }

        SpuAndSkuVO spuAndSku = new SpuAndSkuVO();
        spuAndSku.setSku(sku);
        spuAndSku.setSpu(spu);
        return ServerResponseEntity.success(spuAndSku);
    }

    @Override
    public ServerResponseEntity<List<SpuVO>> getSpusBySpuIds(List<Long> spuIds, String spuName, Integer isFailure) {
        return ServerResponseEntity.success(spuService.listBySpuIds(spuIds, spuName, isFailure));
    }
}
