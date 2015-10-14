package com.puppycrawl.tools.checkstyle.checks.metrics;

public class InputBooleanExpressionComplexityNPE
{
    static {
        try {
            System.out.println("a");
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
