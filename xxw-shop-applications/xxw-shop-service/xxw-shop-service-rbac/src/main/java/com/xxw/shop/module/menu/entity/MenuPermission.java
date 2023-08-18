package com.xxw.shop.module.menu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类。
 *
 * @author liaoxiting
 * @since 2023-07-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "menu_permission")
public class MenuPermission implements Serializable {
    /**
     * 菜单资源用户id
     */
    @Id(keyType = KeyType.Auto)
    private Long menuPermissionId;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;
    /**
     * 资源关联菜单
     */
    private Long menuId;
    /**
     * 业务类型 1 店铺菜单 2平台菜单
     */
    private Integer bizType;
    /**
     * 权限对应的编码
     */
    private String permission;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源对应服务器路径
     */
    private String uri;
    /**
     * 请求方法 1.GET 2.POST 3.PUT 4.DELETE
     */
    private Integer method;

}
