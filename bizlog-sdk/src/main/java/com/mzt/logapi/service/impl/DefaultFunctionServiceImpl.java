package com.mzt.logapi.service.impl;

import com.mzt.logapi.context.FunctionContext;
import com.mzt.logapi.service.IFunctionService;
import com.mzt.logapi.service.IParseFunction;

/**
 * @author muzhantong
 * create on 2021/2/1 5:18 下午
 */
public class DefaultFunctionServiceImpl implements IFunctionService {

    private final ParseFunctionFactory parseFunctionFactory;

    public DefaultFunctionServiceImpl(ParseFunctionFactory parseFunctionFactory) {
        this.parseFunctionFactory = parseFunctionFactory;
    }

    @Override
    public String apply(FunctionContext context) {
        IParseFunction function = parseFunctionFactory.getFunction(context.getFunctionName());
        if (function == null) {
            return context.getValue().toString();
        }
        return function.apply(context);
    }

    @Override
    public boolean beforeFunction(String functionName) {
        return parseFunctionFactory.isBeforeFunction(functionName);
    }
}
