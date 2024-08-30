package com.mzt.logapi.context;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

/**
 * @author MaGuangZu
 * @since 2024-08-14
 */
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DefaultFunctionContext implements FunctionContext {
    /**
     * function name
     */
    private String functionName;
    /**
     * value
     */
    private Object value;
}
