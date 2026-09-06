/*
BooleanExpressionComplexity
max = 1
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF
treatUniformSimpleSequentialExpressionsAsOne = true

*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityUniformChainOperators {

    public boolean notEqualSameVariable(int type) {
        return type != 1 && type != 2 && type != 3;
    }

    public boolean notEqualDifferentVariable(int type, int other) {
        return type != 1 && type != 2 && other != 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean lessThanSameVariable(int type) {
        return type < 1 && type < 2 && type < 3;
    }

    public boolean lessThanDifferentVariable(int type, int other) {
        return type < 1 && type < 2 && other < 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean greaterThanSameVariable(int type) {
        return type > 1 && type > 2 && type > 3;
    }

    public boolean greaterThanDifferentVariable(int type, int other) {
        return type > 1 && type > 2 && other > 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean differentQualifierSameFieldBreaksChain(Holder holder, Holder other) {
        return holder.type == 1 && other.type == 2 && holder.type == 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean arrayAccessDotBreaksChain(Holder[] holders) {
        return holders[0].type == 1 && holders[0].type == 2 && holders[0].type == 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean lessOrEqualSameVariable(int type) {
        return type <= 1 && type <= 2 && type <= 3;
    }

    public boolean lessOrEqualDifferentVariable(int type, int other) {
        return type <= 1 && type <= 2 && other <= 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean greaterOrEqualSameVariable(int type) {
        return type >= 1 && type >= 2 && type >= 3;
    }

    public boolean greaterOrEqualDifferentVariable(int type, int other) {
        return type >= 1 && type >= 2 && other >= 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean qualifiedFieldSameTarget(Holder holder) {
        return holder.type == 1 && holder.type == 2 && holder.type == 3;
    }

    public boolean qualifiedFieldDifferentTarget(Holder holder) {
        return holder.type == 1 && holder.other == 2 && holder.type == 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean qualifiedMethodCallSameTarget(Holder holder) {
        return holder.getType() == 1 && holder.getType() == 2 && holder.getType() == 3;
    }

    public boolean qualifiedMethodCallDifferentTarget(Holder holder, Holder other) {
        return holder.getType() == 1 && holder.getType() == 2 && other.getType() == 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    public boolean unrecognizedLeftHandSide(int a, int b) {
        return a + b == 1 && a + b == 2 && a + b == 3;
        // violation above 'Boolean expression complexity is 2 (max allowed is 1).'
    }

    static class Holder {
        int type;
        int other;

        int getType() {
            return type;
        }
    }

}
