package com.xxw.shop.dto;

import com.xxw.shop.module.common.page.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryDTO extends PageDTO<UserQueryDTO> implements Serializable {

    private static final long serialVersionUID = 1L;
}