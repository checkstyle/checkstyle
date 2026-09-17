/*
BooleanExpressionComplexity
max = (default)3
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF


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
