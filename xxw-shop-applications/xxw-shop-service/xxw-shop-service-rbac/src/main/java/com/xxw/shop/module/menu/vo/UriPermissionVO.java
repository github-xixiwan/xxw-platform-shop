package com.xxw.shop.module.menu.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UriPermissionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求方法 1.GET 2.POST 3.PUT 4.DELETE
     */
    private Integer method;

    /**
     * uri
     */
    private String uri;

    /**
     * permission
     */
    private String permission;
}
