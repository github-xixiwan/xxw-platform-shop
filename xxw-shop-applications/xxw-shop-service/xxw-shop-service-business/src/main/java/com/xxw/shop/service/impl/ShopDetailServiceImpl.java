package com.xxw.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.api.auth.dto.AuthAccountDTO;
import com.xxw.shop.api.auth.feign.AccountFeignClient;
import com.xxw.shop.api.auth.vo.AuthAccountVO;
import com.xxw.shop.api.business.vo.ShopDetailVO;
import com.xxw.shop.cache.BusinessCacheNames;
import com.xxw.shop.constant.BusinessBusinessError;
import com.xxw.shop.constant.ShopStatus;
import com.xxw.shop.constant.ShopType;
import com.xxw.shop.dto.ShopDetailDTO;
import com.xxw.shop.dto.ShopDetailQueryDTO;
import com.xxw.shop.entity.ShopDetail;
import com.xxw.shop.entity.ShopUser;
import com.xxw.shop.mapper.ShopDetailMapper;
import com.xxw.shop.module.common.bo.UserInfoInTokenBO;
import com.xxw.shop.module.common.constant.StatusEnum;
import com.xxw.shop.module.common.constant.SysTypeEnum;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.constant.UserAdminType;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.common.string.PrincipalUtil;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.module.web.util.IpHelper;
import com.xxw.shop.service.ShopDetailService;
import com.xxw.shop.service.ShopUserService;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.xxw.shop.entity.table.ShopDetailTableDef.SHOP_DETAIL;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
@Service
public class ShopDetailServiceImpl extends ServiceImpl<ShopDetailMapper, ShopDetail> implements ShopDetailService {

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AccountFeignClient accountFeignClient;

    @Resource
    private ShopUserService shopUserService;

    @Override
    public Page<ShopDetailVO> page(ShopDetailQueryDTO dto) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_DETAIL.SHOP_STATUS.ne(-1));
        queryWrapper.and(SHOP_DETAIL.SHOP_NAME.like(dto.getShopName()).when(StringUtils.isNotBlank(dto.getShopName())));
        queryWrapper.and(SHOP_DETAIL.SHOP_STATUS.eq(dto.getShopStatus()));
        queryWrapper.orderBy(SHOP_DETAIL.SHOP_ID.desc());
        return this.pageAs(new Page<>(dto.getPageNumber(), dto.getPageSize()), queryWrapper, ShopDetailVO.class);
    }

    @Override
    @Cacheable(cacheNames = BusinessCacheNames.SHOP_DETAIL_ID_KEY, key = "#shopId")
    public ShopDetailVO getByShopId(Long shopId) {
        ServerResponseEntity<AuthAccountVO> accountRes = accountFeignClient.getMerchantInfoByTenantId(shopId);
        if (!accountRes.isSuccess()) {
            throw new BusinessException(BusinessBusinessError.BUSINESS_00001);
        }
        AuthAccountVO authAccountVO = accountRes.getData();
        ShopDetail shopDetail = this.getById(shopId);
        ShopDetailVO vo = mapperFacade.map(shopDetail, ShopDetailVO.class);
        if (Objects.nonNull(authAccountVO)) {
            vo.setUsername(authAccountVO.getUsername());
        }
        return vo;
    }

    @Override
    @CacheEvict(cacheNames = BusinessCacheNames.SHOP_DETAIL_ID_KEY, key = "#shopDetail.shopId")
    public void updateShopDetail(ShopDetail shopDetail) {
        this.updateById(shopDetail);
    }

    @Override
    @CacheEvict(cacheNames = BusinessCacheNames.SHOP_DETAIL_ID_KEY, key = "#shopId")
    public void deleteById(Long shopId) {
        this.removeById(shopId);
    }

    @Override
    public List<ShopDetail> listByShopIds(List<Long> shopIds) {
        if (CollUtil.isEmpty(shopIds)) {
            return new ArrayList<>();
        }
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_DETAIL.SHOP_ID.in(shopIds));
        return this.list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    public void applyShop(ShopDetailDTO dto) {
        checkShopInfo(dto);
        ShopDetail newShopDetail = mapperFacade.map(dto, ShopDetail.class);
        // 申请开店
        newShopDetail.setShopStatus(ShopStatus.OPEN.value());
        newShopDetail.setType(ShopType.STOP.value());
        this.save(newShopDetail);
        dto.setShopId(newShopDetail.getShopId());
        // 创建账号
        createShopAccount(dto, StatusEnum.ENABLE);
    }

    @Override
    public void changeSpuStatus(Long shopId, Integer shopStatus) {
        ShopDetail shopDetail = new ShopDetail();
        shopDetail.setShopId(shopId);
        shopDetail.setShopStatus(shopStatus);
        this.updateById(shopDetail);
    }

    @Override
    @CacheEvict(cacheNames = BusinessCacheNames.SHOP_DETAIL_ID_KEY, key = "#shopId")
    public void removeCacheByShopId(Long shopId) {

    }

    @Override
    public ShopDetailVO shopExtensionData(Long shopId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_DETAIL.SHOP_ID.eq(shopId));
        return this.getOneAs(queryWrapper, ShopDetailVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional(rollbackFor = Exception.class)
    public void createShop(ShopDetailDTO shopDetailDTO) {
        checkShopInfo(shopDetailDTO);
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        if (Objects.nonNull(userInfoInTokenBO.getTenantId())) {
            throw new BusinessException(BusinessBusinessError.BUSINESS_00005);
        }
        // 保存店铺
        ShopDetail shopDetail = mapperFacade.map(shopDetailDTO, ShopDetail.class);
        shopDetail.setShopStatus(ShopStatus.OPEN.value());
        this.save(shopDetail);
        // 保存商家账号
        // 保存到shopUser
        ShopUser shopUser = new ShopUser();
        shopUser.setShopUserId(System.currentTimeMillis());
        shopUser.setShopId(shopDetail.getShopId());
        shopUser.setHasAccount(1);
        shopUser.setNickName(shopDetailDTO.getShopName());
        shopUserService.saveShopUser(shopUser, null);
        // 保存到authAccount
        AuthAccountDTO authAccountDTO = new AuthAccountDTO();
        authAccountDTO.setTenantId(shopDetail.getShopId());
        authAccountDTO.setUsername(shopDetailDTO.getUsername());
        authAccountDTO.setPassword(shopDetailDTO.getPassword());
        authAccountDTO.setCreateIp(IpHelper.getIpAddr());
        authAccountDTO.setStatus(StatusEnum.ENABLE.value());
        authAccountDTO.setSysType(SysTypeEnum.MULTISHOP.value());
        authAccountDTO.setIsAdmin(UserAdminType.ADMIN.value());
        authAccountDTO.setUserId(shopUser.getShopUserId());
        accountFeignClient.save(authAccountDTO);

        userInfoInTokenBO.setTenantId(shopDetail.getShopId());
        ServerResponseEntity<Void> updateTenantIdRes =
                accountFeignClient.updateUserInfoByUserIdAndSysType(userInfoInTokenBO,
                        AuthUserContext.get().getUserId(), SysTypeEnum.ORDINARY.value());
        if (!Objects.equals(updateTenantIdRes.getCode(), SystemErrorEnumError.OK.getCode())) {
            throw new BusinessException(updateTenantIdRes.getMessage());
        }
    }

    @Override
    public List<ShopDetailVO> getShopDetailByShopIdAndShopName(List<Long> shopIds, String shopName) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(SHOP_DETAIL.SHOP_ID.in(shopIds));
        queryWrapper.and(SHOP_DETAIL.SHOP_NAME.like(shopName));
        return this.listAs(queryWrapper, ShopDetailVO.class);
    }

    @Override
    public boolean checkShopName(String shopName) {
        int count = mapper.countShopName(shopName, null);
        return count <= 0;
    }

    /**
     * 检验店铺信息是否正确
     *
     * @param shopDetailDTO
     */
    private void checkShopInfo(ShopDetailDTO shopDetailDTO) {
        // 店铺名称
        if (StrUtil.isNotBlank(shopDetailDTO.getShopName())) {
            shopDetailDTO.setShopName(shopDetailDTO.getShopName().trim());
        }
        if (mapper.countShopName(shopDetailDTO.getShopName(), null) > 0) {
            throw new BusinessException(BusinessBusinessError.BUSINESS_00002);
        }

        String username = shopDetailDTO.getUsername();
        // 用户名
        if (!PrincipalUtil.isUserName(username)) {
            throw new BusinessException(BusinessBusinessError.BUSINESS_00003);
        }

        ServerResponseEntity<AuthAccountVO> accountResponse = accountFeignClient.getByUsernameAndSysType(username,
                SysTypeEnum.MULTISHOP);
        if (!Objects.equals(accountResponse.getCode(), SystemErrorEnumError.OK.getCode())) {
            throw new BusinessException(accountResponse.getMessage());
        }

        AuthAccountVO authAccountVO = accountResponse.getData();
        if (Objects.nonNull(authAccountVO)) {
            throw new BusinessException(BusinessBusinessError.BUSINESS_00004);
        }
    }

    /**
     * 创建店铺初始账号
     *
     * @param shopDetailDTO
     * @param statusEnum
     */
    private void createShopAccount(ShopDetailDTO shopDetailDTO, StatusEnum statusEnum) {
        ShopUser shopUser = new ShopUser();
        shopUser.setShopUserId(System.currentTimeMillis());
        shopUser.setShopId(shopDetailDTO.getShopId());
        shopUser.setHasAccount(1);
        shopUser.setNickName(shopDetailDTO.getUsername());
        shopUserService.saveShopUser(shopUser, null);

        AuthAccountDTO authAccountDTO = new AuthAccountDTO();
        authAccountDTO.setUsername(shopDetailDTO.getUsername());
        String password = passwordEncoder.encode(shopDetailDTO.getPassword().trim());
        authAccountDTO.setPassword(password);
        authAccountDTO.setStatus(statusEnum.value());
        authAccountDTO.setSysType(SysTypeEnum.MULTISHOP.value());
        authAccountDTO.setCreateIp(IpHelper.getIpAddr());
        authAccountDTO.setTenantId(shopDetailDTO.getShopId());
        authAccountDTO.setUserId(shopUser.getShopUserId());
        authAccountDTO.setIsAdmin(UserAdminType.ADMIN.value());
        ServerResponseEntity<Long> save = accountFeignClient.save(authAccountDTO);
        if (!Objects.equals(save.getCode(), SystemErrorEnumError.OK.getCode())) {
            throw new BusinessException(save.getMessage());
        }
    }
}
