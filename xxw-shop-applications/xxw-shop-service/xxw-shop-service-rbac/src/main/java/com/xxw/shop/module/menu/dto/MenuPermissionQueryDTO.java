package com.xxw.shop.module.menu.dto;

import com.xxw.shop.module.web.page.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuPermissionQueryDTO extends PageDTO<MenuPermissionQueryDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer sysType;
}