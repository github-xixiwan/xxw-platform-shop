package com.xxw.shop.module.user.dto;

import com.xxw.shop.module.web.page.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryDTO extends PageDTO<UserQueryDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long shopId;

    private String nickName;
}