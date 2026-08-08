/*
BooleanExpressionComplexity
max = 0
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF
treatUniformSimpleSequentialExpressionsAsOne = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityExcludedNodeNesting {

    private boolean _a;
    private boolean _b;
    private boolean _c;

    void method() {
        new Settings(_a & (_b || _c));
        // violation above 'Boolean expression complexity is 1 (max allowed is 0).'
    }

    private static class Settings {
        Settings(boolean flag) {
        }
    }

}
