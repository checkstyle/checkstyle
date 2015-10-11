//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.coding;

public class InputFinalLocalVariableNameLambda {
    
    private void addTotalValueOfOrder(final AugmentedOrder order) {
    final BigDecimal totalValueOfOrder = order.getTransactions().stream()
            .reduce(BigDecimal.ZERO,
                    (t, u) -> t.add(u.getAmount()),
                    BigDecimal::add);
    order.setTotalValueOfOrder(totalValueOfOrder.toPlainString());
 }
}
