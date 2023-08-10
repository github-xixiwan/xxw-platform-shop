package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.IndexImg;
import com.xxw.shop.vo.IndexImgVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface IndexImgMapper extends BaseMapper<IndexImg> {

    /**
     * 根据店铺id，获取轮播图列表
     *
     * @param shopId
     * @return
     */
    List<IndexImgVO> getListByShopId(@Param("shopId") Long shopId);

    /**
     * 根据spuId清除轮播图的spuId
     *
     * @param spuId
     */
    void clearSpuIdBySpuId(Long spuId);
}
