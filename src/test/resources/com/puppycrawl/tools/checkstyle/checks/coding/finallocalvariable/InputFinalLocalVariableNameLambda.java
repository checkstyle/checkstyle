/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF,VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.math.BigDecimal;


public class InputFinalLocalVariableNameLambda {
    private interface Lambda {
        public Object op(AugmentedOrder a, AugmentedOrder b);
        // 2 violations above:
        //    "Variable 'a' should be declared final"
        //    "Variable 'b' should be declared final"
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
}
interface Operation {
    public Object apply();

    public static final Operation OPERATION = () -> {
        Object result; // violation, "Variable 'result' should be declared final"
        result = null;
        return result;
    };
}
