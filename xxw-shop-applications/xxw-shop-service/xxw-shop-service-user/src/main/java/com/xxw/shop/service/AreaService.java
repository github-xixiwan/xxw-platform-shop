package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.api.user.vo.AreaVO;
import com.xxw.shop.entity.Area;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-11
 */
public interface AreaService extends IService<Area> {

    List<AreaVO> lists();

    List<AreaVO> getAreaListInfo();

    List<AreaVO> listAreaOfEnable();

    AreaVO getByAreaId(Long areaId);

    void deleteById(Long areaId);

    List<AreaVO> listByPid(Long pid);

    void removeAllCache(Long pid);

    void removeAreaCacheByParentId(Long pid);

}
