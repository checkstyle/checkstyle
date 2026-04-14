/*
BooleanExpressionComplexity
max = (default)3
ignoreUniformChains = true
tokens = (default)LAND, BAND, LOR, BOR, BXOR


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityUniformChain {

    private int type;

    // uniform LOR chain: 7 operators, all the same — counts as 1, no violation
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

    // uniform LAND chain: 5 operators, all the same — counts as 1, no violation
    public boolean allConditionsMet(boolean a, boolean b, boolean c,
                                    boolean d, boolean e, boolean f) {
        return a && b && c && d && e && f;
    }

    // mixed: alternating LAND and LOR — each LAND counts separately, total=4, violation
    public boolean mixed(boolean a, boolean b, boolean c,
                         boolean d, boolean e, boolean f) {
        return a && b || c && d || e && f;
        // violation above 'Boolean expression complexity is 4 (max allowed is 3).'
    }

    // two separate uniform chains joined by LAND — each chain root counts as 1,
    // total = 2 operators counted (one LOR root + one LAND), no violation
    public boolean twoChains(boolean a, boolean b, boolean c,
                              boolean d, boolean e, boolean f) {
        return (a || b || c) && (d || e || f);
    }
    // uniform BAND chain with ignoreUniformChains=true — counts as 1, no violation
    public boolean uniformBand(boolean a, boolean b, boolean c,
                               boolean d, boolean e, boolean f) {
        return a & b & c & d & e;
    }

    // uniform BXOR chain with ignoreUniformChains=true — counts as 1, no violation
    public boolean uniformBxor(boolean a, boolean b, boolean c,
                               boolean d, boolean e, boolean f) {
        return a ^ b ^ c ^ d ^ e;
    }

    // uniform BOR chain with ignoreUniformChains=true — counts as 1, no violation
    public boolean uniformBor(boolean a, boolean b, boolean c,
                              boolean d, boolean e, boolean f) {
        return a | b | c | d | e;
    }

}
