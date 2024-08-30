package com.mzt.logserver.function;

import com.mzt.logapi.context.FunctionContext;
import com.mzt.logapi.service.IParseFunction;
import org.springframework.stereotype.Component;

/**
 * @author muzhantong
 * create on 2022/2/17 4:56 PM
 */
@Component
public class DollarParseFunction implements IParseFunction {
    @Override
    public String functionName() {
        return "DOLLAR";
    }

    @Override
    public String apply(FunctionContext context) {
        return "10$,/666";
    }
}
