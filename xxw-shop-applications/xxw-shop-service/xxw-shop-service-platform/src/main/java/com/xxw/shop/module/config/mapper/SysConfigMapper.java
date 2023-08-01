package com.xxw.shop.module.config.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.module.config.entity.SysConfig;
import com.xxw.shop.module.config.vo.SysConfigVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-01
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 获取系统配置信息表列表
     *
     * @return 系统配置信息表列表
     */
    List<SysConfigVO> list();

    /**
     * 根据系统配置信息表id获取系统配置信息表
     *
     * @param id 系统配置信息表id
     * @return 系统配置信息表
     */
    SysConfigVO getById(@Param("id") Long id);

    /**
     * 保存系统配置信息表
     *
     * @param sysConfig 系统配置信息表
     */
    void save(@Param("sysConfig") SysConfig sysConfig);

    /**
     * 更新系统配置信息表
     *
     * @param sysConfig 系统配置信息表
     * @return
     */
    int update(@Param("sysConfig") SysConfig sysConfig);

    /**
     * 根据系统配置key获取对应数量
     *
     * @param paramKey key
     * @return count
     */
    int countByKey(@Param("paramKey") String paramKey);


    /**
     * 根据key，查询系统配置信息
     *
     * @param key key
     * @return SysConfig
     */
    SysConfig queryByKey(@Param("key") String key);
}
