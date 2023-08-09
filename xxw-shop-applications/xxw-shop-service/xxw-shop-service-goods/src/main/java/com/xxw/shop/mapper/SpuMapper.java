package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.Spu;
import com.xxw.shop.vo.SpuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * 根据spu信息id获取spu信息
     *
     * @param spuId spu信息id
     * @return spu信息
     */
    SpuVO getBySpuId(@Param("spuId") Long spuId);

    /**
     * 更新spu表（canal监听后，会发送更新的消息，更新es中的数据）
     *
     * @param spuIds
     * @param categoryIds
     */
    void updateSpuUpdateTime(@Param("spuIds") List<Long> spuIds, @Param("categoryIds") List<Long> categoryIds);
}
