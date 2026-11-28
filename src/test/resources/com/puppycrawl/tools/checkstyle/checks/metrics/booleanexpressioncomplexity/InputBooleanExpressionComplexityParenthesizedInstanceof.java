/*
BooleanExpressionComplexity
max = (default)3
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF
treatUniformExpressionsAsOne = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityParenthesizedInstanceof {

    public boolean parenthesizedInstanceofChain(Object o, Object p, Object q,
            Object r, Object s)
    { // violation below 'Boolean expression complexity is 4 (max allowed is 3)'
        return (o) instanceof String && (p) instanceof Integer
                && (q) instanceof Long && (r) instanceof Double
                && (s) instanceof Float;
    }

    public boolean parenthesizedInstanceofSameVariable(Object o) {
        return (o) instanceof String && (o) instanceof Comparable
                && (o) instanceof java.io.Serializable;
    }

}
