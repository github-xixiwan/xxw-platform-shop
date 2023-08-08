package com.xxw.shop.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.SpuExtensionQueryDTO;
import com.xxw.shop.entity.SpuExtension;
import com.xxw.shop.vo.SpuExtensionVO;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SpuExtensionService extends IService<SpuExtension> {

    Page<SpuExtensionVO> page(SpuExtensionQueryDTO dto);

    SpuExtensionVO getBySpuExtendId(Long spuExtendId);

    void updateStock(Long spuId, Integer count);

    void deleteBySpuId(Long spuId);

    SpuExtensionVO getBySpuId(Long spuId);
}
