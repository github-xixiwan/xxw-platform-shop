package com.xxw.shop.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.BrandDTO;
import com.xxw.shop.dto.BrandQueryDTO;
import com.xxw.shop.entity.Brand;
import com.xxw.shop.vo.BrandVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-08
 */
public interface BrandService extends IService<Brand> {

    Page<BrandVO> page(BrandQueryDTO dto);

    BrandVO getByBrandId(Long brandId);

    void saveBrand(BrandDTO dto, List<Long> categoryIds);

    void updateBrand(BrandDTO dto, List<Long> categoryIds);

    void deleteById(Long brandId);

    List<BrandVO> getBrandByCategoryId(Long categoryId);

    void updateBrandStatus(BrandDTO dto);

    List<BrandVO> listByCategory(Long categoryId);

    List<BrandVO> topBrandList();

    void removeCache(List<Long> categoryIds);
}
