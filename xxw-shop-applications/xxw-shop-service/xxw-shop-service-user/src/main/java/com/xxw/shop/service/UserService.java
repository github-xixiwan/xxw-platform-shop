package com.xxw.shop.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.api.user.vo.UserCompleteVO;
import com.xxw.shop.dto.UserQueryDTO;
import com.xxw.shop.dto.UserRegisterDTO;
import com.xxw.shop.entity.User;

import java.util.List;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-11
 */
public interface UserService extends IService<User> {

    Page<UserCompleteVO> page(UserQueryDTO dto);

    UserCompleteVO getByUserId(Long userId);

    void updateUser(User user);

    List<UserCompleteVO> getUserByUserIds(List<Long> userIds);

    Long saveUser(UserRegisterDTO param);
}
