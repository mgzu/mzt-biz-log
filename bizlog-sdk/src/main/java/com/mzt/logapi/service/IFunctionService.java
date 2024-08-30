package com.mzt.logapi.service;

import com.mzt.logapi.context.FunctionContext;

public interface IFunctionService {

    String apply(FunctionContext context);

    boolean beforeFunction(String functionName);

}
