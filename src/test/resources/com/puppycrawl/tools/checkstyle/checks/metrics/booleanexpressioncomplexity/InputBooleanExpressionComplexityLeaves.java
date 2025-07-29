/*
BooleanExpressionComplexity


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityLeaves {

    public InputBooleanExpressionComplexityLeaves() {
    }

    public static final ConstructingObjectParserOne PARSER = new ConstructingObjectParserOne(
        "restore_snapshot",
        true,
        () -> {
            Boolean accepted = (Boolean) null;
            assert (accepted == null && null == null) || (accepted != null && accepted && !accepted)
                : "accepted: [" + accepted + "], restoreInfo: [" + 0 + "]";
            return;
        }
    );
}
class ConstructingObjectParserOne {
    public ConstructingObjectParserOne(String name, boolean value, Runnable t) {}
}
