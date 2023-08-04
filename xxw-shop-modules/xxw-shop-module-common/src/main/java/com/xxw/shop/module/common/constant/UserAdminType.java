package com.xxw.shop.module.common.constant;

public enum UserAdminType {
    /**
     * 管理员
     */
    ADMIN(1),
    /**
     * 用户
     */
    USER(0),
    ;

    private Integer value;

    public Integer value() {
        return value;
    }

    public Integer getValue() {
        return value;
    }


    UserAdminType(Integer value) {
        this.value = value;
    }

    public static UserAdminType instance(Integer value) {
        UserAdminType[] enums = values();
        for (UserAdminType statusEnum : enums) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
