/*
BooleanExpressionComplexity
max = 1
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF
treatUniformSimpleSequentialExpressionsAsOne = true

*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityUniformChain {

    public boolean allConditionsMet(boolean a, boolean b, boolean c,
                                     boolean d, boolean e, boolean f) {
        return a && b && c && d && e && f;
    }

    public boolean anyConditionMet(boolean a, boolean b, boolean c,
                                    boolean d, boolean e, boolean f) {
        return a || b || c || d || e || f;
    }

    public boolean equalityChainSameVariable(int type) {
        return type == 1 && type == 2 && type == 3;
    }

    public boolean equalityChainSameMethodCall() {
        return getType() == 1 && getType() == 2 && getType() == 3;
    }

    public boolean nestedParenBreaksChain(boolean a, boolean b, boolean c) {
        return a && (b && c);
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean differentVariableBreaksChain(int type, int someOther) {
        return type == 1 && type == 2 && someOther == 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean differentMethodCallBreaksChain() {
        return getType() == 1 && getSomeOther() == 2 && getType() == 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean twoChains(boolean a, boolean b, boolean c,
                              boolean d, boolean e, boolean f) {
        return (a || b || c) && (d || e || f);
        // violation above 'Boolean expression complexity is 3 (max allowed is 1).'
    }

    public boolean deepNestedRegroupBreaksChain(boolean a, boolean b, boolean c,
                                                boolean d, boolean e) {
        return a && (b && c) && d && e;
        // violation above 'Boolean expression complexity is 4 (max allowed is 1).'
    }

    public boolean firstOperandDifferentBreaksChain(int type, int someOther) {
        return someOther == 1 && type == 2 && type == 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean singlePair(boolean a, boolean b) {
        return a && b;
    }

    private int getType() {
        return 1;
    }

    private int getSomeOther() {
        return 1;
    }

}
