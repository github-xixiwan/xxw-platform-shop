package com.xxw.shop.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.dto.HotSearchDTO;
import com.xxw.shop.dto.HotSearchQueryDTO;
import com.xxw.shop.entity.HotSearch;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.HotSearchService;
import com.xxw.shop.vo.HotSearchVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 热搜
 */
@RestController("adminHotSearchController")
@RequestMapping("/admin/hot_search")
@Tag(name = "admin-热搜")
public class HotSearchController {

    @Resource
    private HotSearchService hotSearchService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/page")
    @Operation(summary = "分页获取热搜列表", description = "分页获取热搜列表")
    public ServerResponseEntity<Page<HotSearchVO>> page(@Valid HotSearchQueryDTO dto) {
        dto.setShopId(AuthUserContext.get().getTenantId());
        return ServerResponseEntity.success(hotSearchService.page(dto));
    }

    @GetMapping
    @Operation(summary = "获取热搜", description = "根据hotSearchId获取热搜")
    public ServerResponseEntity<HotSearchVO> getByHotSearchId(@RequestParam Long hotSearchId) {
        return ServerResponseEntity.success(hotSearchService.getByHotSearchId(hotSearchId));
    }

    @PostMapping
    @Operation(summary = "保存热搜", description = "保存热搜")
    public ServerResponseEntity<Void> save(@Valid @RequestBody HotSearchDTO dto) {
        HotSearch hotSearch = mapperFacade.map(dto, HotSearch.class);
        hotSearch.setShopId(AuthUserContext.get().getTenantId());
        hotSearchService.save(hotSearch);
        hotSearchService.removeHotSearchListCache(hotSearch.getShopId());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新热搜", description = "更新热搜")
    public ServerResponseEntity<Void> update(@Valid @RequestBody HotSearchDTO dto) {
        HotSearch hotSearch = mapperFacade.map(dto, HotSearch.class);
        hotSearch.setShopId(AuthUserContext.get().getTenantId());
        hotSearchService.updateById(hotSearch);
        hotSearchService.removeHotSearchListCache(hotSearch.getShopId());
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除热搜", description = "根据热搜id删除热搜")
    public ServerResponseEntity<Void> delete(@RequestParam Long hotSearchId) {
        Long shopId = AuthUserContext.get().getTenantId();
        hotSearchService.deleteById(hotSearchId, shopId);
        hotSearchService.removeHotSearchListCache(shopId);
        return ServerResponseEntity.success();
    }
}
