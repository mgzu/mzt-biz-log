package com.mzt.logserver.function;

import com.mzt.logapi.context.DiffFunctionContext;
import com.mzt.logapi.service.IParseFunction;
import com.mzt.logserver.annotations.LogRecordFieldEnum;
import com.mzt.logserver.enums.IBizLogEnum;
import org.springframework.stereotype.Component;

/**
 * @author MaGuangZu
 * @since 2024-08-15
 */
@Component
public class EnumParseFunction implements IParseFunction<DiffFunctionContext> {

    @Override
    public String functionName() {
        return "ENUM";
    }

    @Override
    public String apply(DiffFunctionContext context) {
        if (context.getValue() == null) {
            return null;
        }
        LogRecordFieldEnum annotation = context.getNode().getFieldAnnotation(LogRecordFieldEnum.class);
        if (annotation == null) {
            return context.getValue().toString();
        }
        IBizLogEnum[] enumConstants = annotation.value().getEnumConstants();
        if (enumConstants.length == 0) {
            return null;
        }
        return enumConstants[0].getBizLogByValue(context.getValue());
    }
}
