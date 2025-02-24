/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF,VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.math.BigDecimal;
import java.util.function.Function;

public class InputFinalLocalVariableNameLambda {
    private interface Lambda {
        public Object op(AugmentedOrder a, AugmentedOrder b);
    }
    private static class AugmentedOrder {
        public BigDecimal reduce(final BigDecimal zero, final Lambda l) {
            return null;
        }
        public Object add(final Object amount) {
            return null;
        }
        public Object getAmount() {
            return null;
        }
    }

    private void addTotalValueOfOrder(final AugmentedOrder order) {
    final BigDecimal totalValueOfOrder = order
            .reduce(BigDecimal.ZERO,
                    (t, u) -> t.add(u.getAmount()));
 }
    public static void main(final String[] args) {
        final Function<Integer, Integer> doubleValue = (x) -> { return x * 2; };
    }
}
interface Operation {
    public Object apply();

    public static final Operation OPERATION = () -> {
        Object result; // violation, "Variable 'result' should be declared final"
        result = null;
        return result;
    };
}
