/*
BooleanExpressionComplexity
max = (default)3
tokens = (default)LAND, BAND, LOR, BOR, BXOR


*/

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
