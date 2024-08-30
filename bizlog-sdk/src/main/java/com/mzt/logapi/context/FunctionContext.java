package com.mzt.logapi.context;

import org.springframework.lang.NonNull;

/**
 * @author MaGuangZu
 * @since 2024-08-14
 */
public interface FunctionContext {
    /**
     * function name
     */
    @NonNull
    String getFunctionName();

    /**
     * value
     */
    @NonNull
    Object getValue();

}
