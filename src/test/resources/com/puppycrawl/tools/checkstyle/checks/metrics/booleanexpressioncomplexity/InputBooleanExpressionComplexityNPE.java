/*
BooleanExpressionComplexity
max = (default)3
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF


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
