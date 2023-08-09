package com.xxw.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xxw.shop.entity.Brand;
import com.xxw.shop.api.goods.vo.BrandVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 根据品牌信息id获取品牌信息
     *
     * @param brandId 品牌信息id
     * @return 品牌信息
     */
    BrandVO getByBrandId(@Param("brandId") Long brandId);

    /**
     * 根据分类id，获取品牌数据
     *
     * @param categoryId
     * @return
     */
    List<BrandVO> getBrandByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 根据分类id，获取品牌列表(分类中的推荐品牌)
     *
     * @param categoryId
     * @return
     */
    List<BrandVO> listByCategory(@Param("categoryId") Long categoryId);

    /**
     * 获取置顶品牌列表
     *
     * @return
     */
    List<BrandVO> topBrandList();
}
