package com.mzt.logapi.service.impl;

import com.mzt.logapi.context.FunctionContext;
import com.mzt.logapi.service.IParseFunction;

/**
 * @author muzhantong
 * create on 2021/2/6 9:58 上午
 */
public class DefaultParseFunction implements IParseFunction {

    @Override
    public boolean executeBefore() {
        return true;
    }

    @Override
    public String functionName() {
        return null;
    }

    @Override
    public String apply(FunctionContext context) {
        return null;
    }
}
