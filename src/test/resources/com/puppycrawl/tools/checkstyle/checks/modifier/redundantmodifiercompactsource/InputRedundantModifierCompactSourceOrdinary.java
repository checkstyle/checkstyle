/*
RedundantModifierCompactSource
tokens = (default)METHOD_DEF, VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifiercompactsource;

final class InputRedundantModifierCompactSourceOrdinary {
    public int publicField;
    protected int protectedField;
    private int privateField;
    static int staticField;

    public void publicMethod() {}
    protected void protectedMethod() {}
    private void privateMethod() {}
    static void staticMethod() {}
    final void finalized() {}
    strictfp double calculate() { return 1.0; }
}
