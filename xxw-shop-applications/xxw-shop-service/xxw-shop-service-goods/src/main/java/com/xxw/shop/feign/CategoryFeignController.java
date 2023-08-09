package com.xxw.shop.feign;

import com.xxw.shop.api.goods.feign.CategoryFeignClient;
import com.xxw.shop.api.goods.vo.CategoryVO;
import com.xxw.shop.module.common.constant.Constant;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryFeignController implements CategoryFeignClient {

    @Resource
    private CategoryService categoryService;

    @Override
    public ServerResponseEntity<List<CategoryVO>> listByOneLevel() {
        return ServerResponseEntity.success(categoryService.listByShopIdAndParenId(Constant.PLATFORM_SHOP_ID,
                Constant.CATEGORY_ID));
    }

    @Override
    public ServerResponseEntity<List<Long>> listCategoryId(Long categoryId) {
        CategoryVO category = categoryService.getCategoryId(categoryId);
        List<Long> categoryIds = categoryService.listCategoryId(category.getShopId(), category.getParentId());
        return ServerResponseEntity.success(categoryIds);
    }
}
