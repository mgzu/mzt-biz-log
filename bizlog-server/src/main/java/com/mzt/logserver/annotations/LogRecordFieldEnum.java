package com.mzt.logserver.annotations;

import com.mzt.logserver.enums.IBizLogEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MaGuangZu
 * @since 2024-08-15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LogRecordFieldEnum {

    Class<? extends IBizLogEnum> value();

}
