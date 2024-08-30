package com.mzt.logserver.enums;

import cn.hutool.core.util.EnumUtil;
import lombok.Getter;

/**
 * @author MaGuangZu
 * @since 2024-08-15
 */
@Getter
public enum UserStatusEnum implements IBizLogEnum {

    NORMAL(0, "正常"),
    LOCKED(1, "锁定"),
    FORBIDDEN(2, "禁用"),
    DELETE(3, "删除"),
    ;

    private final Integer value;
    private final String description;

    UserStatusEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getBizLogByValue(Object value) {
        UserStatusEnum[] enumConstants = this.getClass().getEnumConstants();
        for (UserStatusEnum enumConstant : enumConstants) {
            if (enumConstant.getValue().equals(value)) {
                return enumConstant.getDescription();
            }
        }
        return null;
    }
}
