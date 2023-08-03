package com.xxw.shop.module.web.page;

import com.xxw.shop.module.common.string.PrincipalUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageDTO<T extends PageDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

    private int pageNumber = 1;

    private int pageSize = 20;

    private String[] columns;

    private String[] orders;

    public static String order(String[] columns, String[] orders) {

        if (columns == null || columns.length == 0) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int x = 0; x < columns.length; x++) {

            String column = columns[x];
            String order;

            if (orders != null && orders.length > x) {
                order = orders[x].toUpperCase();
                if (!(order.equals(ASC) || order.equals(DESC))) {
                    throw new IllegalArgumentException("非法的排序策略：" + column);
                }
            } else {
                order = ASC;
            }

            // 判断列名称的合法性，防止SQL注入。只能是【字母，数字，下划线】
            if (PrincipalUtil.isField(column)) {
                throw new IllegalArgumentException("非法的排序字段名称：" + column);
            }

            // 驼峰转换为下划线
            column = humpConversionUnderscore(column);

            if (x != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("`").append(column).append("` ").append(order);
        }
        return stringBuilder.toString();
    }

    public static String humpConversionUnderscore(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = value.toCharArray();
        for (char character : chars) {
            if (Character.isUpperCase(character)) {
                stringBuilder.append("_");
                character = Character.toLowerCase(character);
            }
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }
}
