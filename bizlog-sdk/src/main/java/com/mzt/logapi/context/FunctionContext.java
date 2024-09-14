package com.mzt.logapi.context;

import org.springframework.lang.NonNull;

/**
 * @author MaGuangZu
 * @since 2024-08-14
 */
public interface FunctionContext {
    /**
     * @return the function name
     */
    @NonNull
    String getFunctionName();

    /**
     * @return target value
     */
    @NonNull
    Object getValue();

}
