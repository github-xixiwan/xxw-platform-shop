package com.xxw.shop.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.IndexImgQueryDTO;
import com.xxw.shop.entity.IndexImg;
import com.xxw.shop.vo.IndexImgVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface IndexImgService extends IService<IndexImg> {

    Page<IndexImgVO> page(IndexImgQueryDTO dto);

    IndexImgVO getByImgId(Long imgId);

    void saveIndexImg(IndexImg indexImg);

    void updateIndexImg(IndexImg indexImg);

    void deleteById(Long imgId, Long shopId);

    List<IndexImgVO> getListByShopId(Long shopId);

    void deleteBySpuId(Long spuId, Long shopId);
}
