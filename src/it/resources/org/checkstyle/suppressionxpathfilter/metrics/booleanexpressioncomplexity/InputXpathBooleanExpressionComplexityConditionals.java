/*
BooleanExpressionComplexity
max = (default)3
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.metrics.booleanexpressioncomplexity;

public class InputXpathBooleanExpressionComplexityConditionals {
    public void methodThree() {
        boolean a = true;
        boolean b = false;
        boolean c = true;

        // violation below 'Boolean expression complexity is 4 (max allowed is 3).'
        if (((a && (b & a)) || (b ^ a))) {
            a = b;
        } else if ((a || b) ^ (a && b)) {
            c = b;
        }
    }
}
