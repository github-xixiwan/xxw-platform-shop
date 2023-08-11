package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.api.user.vo.AreaVO;
import com.xxw.shop.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-11
 */
public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 获取省市区三级结构完整的集合
     *
     * @return 省市区三级结构完整的集合
     */
    List<AreaVO> getAreaListInfo();

    /**
     * 获取可用的省市区列表
     *
     * @return
     */
    List<AreaVO> listAreaOfEnable();

    /**
     * 获取该地址id下的下级地址数量
     *
     * @param areaId
     * @return
     */
    int countByAreaId(@Param("areaId") Long areaId);
}
