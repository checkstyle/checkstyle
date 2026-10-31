/*
BooleanExpressionComplexity
max = (default)3
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.metrics.booleanexpressioncomplexity;

public class InputXpathBooleanExpressionComplexityClassFields {
    public void methodTwo() {
        boolean a = true;
        boolean b = false;

        boolean c = (a & b) | (b ^ a);
        // violation below 'Boolean expression complexity is 11 (max allowed is 3).'
        boolean d = (a & b) | (b ^ a) | (a ^ b) |
                (a & b) | (b ^ a) | (a ^ b);
    }
}
