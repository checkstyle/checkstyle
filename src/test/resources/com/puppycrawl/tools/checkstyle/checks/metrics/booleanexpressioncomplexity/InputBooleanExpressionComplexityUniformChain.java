/*
BooleanExpressionComplexity
max = (default)3
ignoreUniformChains = true
tokens = (default)LAND, BAND, LOR, BOR, BXOR


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityUniformChain {

    private int type;

    public boolean isDefinitionToken() {
        return type == 1
            || type == 2
            || type == 3
            || type == 4
            || type == 5
            || type == 6
            || type == 7
            || type == 8;
    }

    public boolean allConditionsMet(boolean a, boolean b, boolean c,
                                    boolean d, boolean e, boolean f) {
        return a && b && c && d && e && f;
    }

    public boolean mixed(boolean a, boolean b, boolean c,
                         boolean d, boolean e, boolean f) {
        return a && b || c && d || e && f;
        // violation above 'Boolean expression complexity is 4 (max allowed is 3).'
    }

    public boolean twoChains(boolean a, boolean b, boolean c,
                              boolean d, boolean e, boolean f) {
        return (a || b || c) && (d || e || f);
    }

    public boolean uniformBand(boolean a, boolean b, boolean c,
                               boolean d, boolean e, boolean f) {
        return a & b & c & d & e;
    }

    public boolean uniformBxor(boolean a, boolean b, boolean c,
                               boolean d, boolean e, boolean f) {
        return a ^ b ^ c ^ d ^ e;
    }

    public boolean uniformBor(boolean a, boolean b, boolean c,
                              boolean d, boolean e, boolean f) {
        return a | b | c | d | e;
    }

    public boolean mixedBor(boolean a, boolean b, boolean c,
                            boolean d, boolean e, boolean f) {
        return (a & b) | (c & d) | (e & f);
        // violation above 'Boolean expression complexity is 4 (max allowed is 3).'
    }

    public boolean longUniformBor(boolean a, boolean b, boolean c,
                                  boolean d, boolean e, boolean f) {
        return a | b | c | d | e | f;
    }

}
