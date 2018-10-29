package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityNPE
{
    static {
        try {
            System.identityHashCode("a");
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
