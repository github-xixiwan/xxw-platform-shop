package com.xxw.shop.module.common.exception;

/**
 * 错误枚举类定义
 *
 * @author xxw
 * @since 2019/11/14
 */
public interface ErrorEnumInterface {

    /**
     * 获取code
     *
     * @return 错误code
     */
    String getCode();

    /**
     * 获取message
     *
     * @return 错误message
     */
    String getMessage();

}
