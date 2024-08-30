package com.mzt.logserver.enums;

import lombok.Getter;

/**
 * @author MaGuangZu
 * @since 2024-08-15
 */
@Getter
public enum UserSexEnum implements IBizLogEnum {

    /**
     * 男
     */
    MALE("MALE", "男"),
    /**
     * 女
     */
    FEMALE("FEMALE", "女"),
    ;

    private final String code;
    private final String name;

    UserSexEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getBizLogByValue(Object value) {
        UserSexEnum[] enumConstants = this.getClass().getEnumConstants();
        for (UserSexEnum enumConstant : enumConstants) {
            if (enumConstant.getCode().equals(value)) {
                return enumConstant.getName();
            }
        }
        return null;
    }
}
