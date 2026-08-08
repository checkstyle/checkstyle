/*
BooleanExpressionComplexity
max = 1
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF
treatUniformSimpleSequentialExpressionsAsOne = true

*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityUniformChainCastTarget {

    public boolean castQualifiedMethodCallTarget(Object obj) {
        return ((Holder) obj).getType() == 1 && ((Holder) obj).getType() == 2
                && ((Holder) obj).getType() == 3;
        // violation 2 lines above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    static class Holder {
        int type;

        int getType() {
            return type;
        }
    }

}
