package com.xxw.shop.starter.canal.annotation;

import com.xxw.shop.starter.canal.common.FieldNamingPolicy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CanalModel {

    /**
     * 目标数据库
     */
    String database();

    /**
     * 目标表
     */
    String table();

    /**
     * 属性名 -> 列名命名转换策略
     */
    FieldNamingPolicy fieldNamingPolicy() default FieldNamingPolicy.DEFAULT;
}
