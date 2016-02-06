package com.puppycrawl.tools.checkstyle.checks.coding;

import java.math.BigDecimal;

public class InputFinalLocalVariableNameLambda {
    private static class AugmentedOrder {
        public void setTotalValueOfOrder(final String plainString) {
        }
    }

    private void addTotalValueOfOrder(final AugmentedOrder order) {
    final BigDecimal totalValueOfOrder = order.getTransactions().stream()
            .reduce(BigDecimal.ZERO,
                    (t, u) -> t.add(u.getAmount()),
                    BigDecimal::add);
    order.setTotalValueOfOrder(totalValueOfOrder.toPlainString());
 }
}
