/*
BooleanExpressionComplexity
max = (default)3
tokens = (default)LAND,BAND,LOR,BOR,BXOR

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public record InputBooleanExpressionComplexityRecordLeaves() {
    public InputBooleanExpressionComplexityRecordLeaves {
    }

    public static final ConstructingObjectParser PARSER = new ConstructingObjectParser(
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
class ConstructingObjectParser {
    public ConstructingObjectParser(String name, boolean value, Runnable t) {}
}
