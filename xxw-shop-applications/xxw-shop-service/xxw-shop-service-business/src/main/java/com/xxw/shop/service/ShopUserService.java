package com.xxw.shop.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.api.auth.vo.AuthAccountVO;
import com.xxw.shop.dto.ChangeAccountDTO;
import com.xxw.shop.dto.ShopUserQueryDTO;
import com.xxw.shop.entity.ShopUser;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.vo.ShopUserVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface ShopUserService extends IService<ShopUser> {
    Page<ShopUserVO> page(ShopUserQueryDTO dto);

    ShopUserVO getByUserId(Long userId);

    void saveShopUser(ShopUser shopUser, List<Long> roleIds);

    void updateShopUser(ShopUser shopUser, List<Long> roleIds);

    void deleteById(Long shopUserId);

    Long getUserIdByShopId(Long shopId);

    ServerResponseEntity<Void> saveChangeAccount(ChangeAccountDTO changeAccountDTO);

    ServerResponseEntity<Void> updateChangeAccount(ChangeAccountDTO changeAccountDTO);

    ServerResponseEntity<AuthAccountVO> getByUserIdAndSysType(Long userId, Integer sysType);
}
