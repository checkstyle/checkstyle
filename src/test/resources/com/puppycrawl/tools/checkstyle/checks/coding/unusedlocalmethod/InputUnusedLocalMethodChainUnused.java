/*
UnusedLocalMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

public class InputUnusedLocalMethodChainUnused {
    public InputUnusedLocalMethodChainUnused() {
        used();
    }

    private void used() {
        checkUsageWithCommentsForActualMethodCallNotStringContains();
    }

    private void checkUsageWithCommentsForActualMethodCallNotStringContains() {
        // unused2 is unused but mention in comment
        // unused2();
        // even multiple twice at the end unused2();
        // even multiple twice at the end unused2(); and in between
    }

    private void unused2() { // violation, "Unused local method 'unused2'"
    }
}
