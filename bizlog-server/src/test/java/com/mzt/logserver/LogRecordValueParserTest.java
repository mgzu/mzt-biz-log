package com.mzt.logserver;

import com.mzt.logapi.starter.support.parse.LogFunctionParser;
import com.mzt.logapi.starter.support.parse.LogRecordValueParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LogRecordValueParserTest {

    private final LogRecordValueParser logRecordValueParser = new LogRecordValueParser();
    private final LogFunctionParser logFunctionParser = mock(LogFunctionParser.class);
    private final EvaluationContext evaluationContext = mock(EvaluationContext.class);

    @BeforeEach
    public void setUp() {
        logRecordValueParser.setLogFunctionParser(logFunctionParser);
    }

    @Test
    void processBeforeExecuteFunctionTemplateReturnsExpectedMap() {
        when(logFunctionParser.beforeFunction(anyString())).thenReturn(true);
        when(logFunctionParser.getFunctionReturnValue(any(), any(), any(), any())).thenReturn("returnValue");
        when(logFunctionParser.getFunctionCallInstanceKey(any(), any())).thenReturn("functionKey");

        Map<String, String> result = logRecordValueParser.processBeforeExecuteFunctionTemplate(
                Collections.singletonList("{template{'xx'}}"), this.getClass(), this.getClass().getMethods()[0], new Object[]{});

        assertEquals(1, result.size());
        assertTrue(result.containsKey("functionKey"));
        assertEquals("returnValue", result.get("functionKey"));
    }

    @Test
    void processBeforeExecuteFunctionTemplateSkipsRetAndErrorMsgExpressions() {
        Map<String, String> result = logRecordValueParser.processBeforeExecuteFunctionTemplate(
                Arrays.asList("{functionName{#_ret}}", "{functionName{#_errorMsg}}"), this.getClass(), this.getClass().getMethods()[0], new Object[]{});

        assertTrue(result.isEmpty());
    }

    @Test
    void processBeforeExecuteFunctionTemplateReturnsEmptyMapForNonMatchingTemplates() {
        Map<String, String> result = logRecordValueParser.processBeforeExecuteFunctionTemplate(
                Collections.singletonList("nonMatchingTemplate"), this.getClass(), this.getClass().getMethods()[0], new Object[]{});

        assertTrue(result.isEmpty());
    }
}
