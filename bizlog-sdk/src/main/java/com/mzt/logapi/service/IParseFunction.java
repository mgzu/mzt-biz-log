package com.mzt.logapi.service;

import com.mzt.logapi.context.FunctionContext;

public interface IParseFunction<T extends FunctionContext> {

    default boolean executeBefore() {
        return false;
    }

    String functionName();

    /**
     * @param context 上下文
     * @return 文案
     * @since 1.1.0 参数从String 修改为Object类型，可以处理更多的场景，可以通过SpEL表达式传递对象了
     * 老版本需要改下自定义函数的声明，实现使用中把 用到 value的地方修改为 value.toString 就可以兼容了
     */
    String apply(T context);
}
