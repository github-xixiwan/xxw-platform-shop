package com.xxw.shop.starter.canal.annotation;

import com.xxw.shop.starter.canal.support.parser.converter.BaseCanalFieldConverter;
import com.xxw.shop.starter.canal.support.parser.converter.NullCanalFieldConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.JDBCType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CanalField {

    /**
     * 行名称
     *
     * @return columnName
     */
    String columnName() default "";

    /**
     * sql字段类型
     *
     * @return JDBCType
     */
    JDBCType sqlType() default JDBCType.NULL;

    /**
     * 转换器类型
     *
     * @return klass
     */
    Class<? extends BaseCanalFieldConverter<?>> converterKlass() default NullCanalFieldConverter.class;
}
