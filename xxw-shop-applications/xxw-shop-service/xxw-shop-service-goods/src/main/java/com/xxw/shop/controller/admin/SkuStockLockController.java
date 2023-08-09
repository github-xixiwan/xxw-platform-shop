package com.xxw.shop.controller.admin;

import com.mybatisflex.core.paginate.Page;
import com.xxw.shop.api.goods.dto.SkuStockLockDTO;
import com.xxw.shop.dto.SkuStockLockQueryDTO;
import com.xxw.shop.entity.SkuStockLock;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.service.SkuStockLockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 库存锁定信息
 */
@RestController("adminSkuStockLockController")
@RequestMapping("/admin/sku_stock_lock")
@Tag(name = "库存锁定信息")
public class SkuStockLockController {

    @Resource
    private SkuStockLockService skuStockLockService;

    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("/page")
    @Operation(summary = "获取库存锁定信息列表", description = "分页获取库存锁定信息列表")
    public ServerResponseEntity<Page<SkuStockLock>> page(@Valid SkuStockLockQueryDTO dto) {
        return ServerResponseEntity.success(skuStockLockService.page(dto));
    }

    @GetMapping
    @Operation(summary = "获取库存锁定信息", description = "根据id获取库存锁定信息")
    public ServerResponseEntity<SkuStockLock> getById(@RequestParam Long id) {
        return ServerResponseEntity.success(skuStockLockService.getById(id));
    }

    @PostMapping
    @Operation(summary = "保存库存锁定信息", description = "保存库存锁定信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody SkuStockLockDTO skuStockLockDTO) {
        SkuStockLock skuStockLock = mapperFacade.map(skuStockLockDTO, SkuStockLock.class);
        skuStockLock.setId(null);
        skuStockLockService.save(skuStockLock);
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新库存锁定信息", description = "更新库存锁定信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody SkuStockLockDTO skuStockLockDTO) {
        SkuStockLock skuStockLock = mapperFacade.map(skuStockLockDTO, SkuStockLock.class);
        skuStockLockService.updateById(skuStockLock);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除库存锁定信息", description = "根据库存锁定信息id删除库存锁定信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long id) {
        skuStockLockService.removeById(id);
        return ServerResponseEntity.success();
    }
}
