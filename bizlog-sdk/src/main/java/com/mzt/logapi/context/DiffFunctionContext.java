package com.mzt.logapi.context;

import de.danielbechler.diff.node.DiffNode;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

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
public class DiffFunctionContext implements FunctionContext {
    /**
     * function name
     */
    private String functionName;
    /**
     * value
     */
    private Object value;
    /**
     * target object
     */
    @Nullable
    private Object target;
    /**
     * diff node
     */
    @Nullable
    private DiffNode node;
}
