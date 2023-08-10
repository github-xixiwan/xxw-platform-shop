package com.xxw.shop.service;

import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.PayInfoDTO;
import com.xxw.shop.entity.PayInfo;
import com.xxw.shop.vo.PayInfoResultVO;
import com.xxw.shop.vo.PayInfoVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface PayInfoService extends IService<PayInfo> {

    PayInfoVO pay(Long userId, PayInfoDTO dto);

    void paySuccess(PayInfoResultVO payInfoResult, List<Long> orderIds);

    Integer getPayStatusByOrderIds(String orderIds);

    Integer isPay(String orderIds, Long userId);
}
