package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.SkuStockLock;
import com.xxw.shop.vo.SkuStockLockVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface SkuStockLockMapper extends BaseMapper<SkuStockLock> {

    /**
     * 根据订单号获取锁定的库存
     *
     * @param orderIds 订单号
     * @return 锁定的库存信息
     */
    List<SkuStockLockVO> listByOrderIds(@Param("orderIds") List<Long> orderIds);

    /**
     * 根据订单号获取已取消锁定的库存
     *
     * @param orderIds 订单号
     * @return 锁定的库存信息
     */
    List<SkuStockLockVO> listUnLockByOrderIds(@Param("orderIds") List<Long> orderIds);

    /**
     * 正式锁定库存，标记为使用状态
     *
     * @param orderIds 锁定库存的订单
     */
    void markerStockUse(@Param("orderIds") List<Long> orderIds);

    /**
     * 将锁定状态标记为已解锁
     *
     * @param lockIds ids
     */
    void unLockByIds(@Param("lockIds") List<Long> lockIds);
}
