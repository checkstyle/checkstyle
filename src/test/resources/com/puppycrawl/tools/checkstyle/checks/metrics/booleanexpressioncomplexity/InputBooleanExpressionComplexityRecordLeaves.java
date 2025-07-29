/*
BooleanExpressionComplexity


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public record InputBooleanExpressionComplexityRecordLeaves() {
    public InputBooleanExpressionComplexityRecordLeaves {
    }

    public static final ConstructingObjectParserTwo PARSER = new ConstructingObjectParserTwo(
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
class ConstructingObjectParserTwo {
    public ConstructingObjectParserTwo(String name, boolean value, Runnable t) {}
}
