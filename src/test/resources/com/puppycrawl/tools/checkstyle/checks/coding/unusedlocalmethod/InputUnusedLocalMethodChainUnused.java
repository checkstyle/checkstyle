/*
UnusedLocalMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

public class InputUnusedLocalMethodChainUnused {
    public InputUnusedLocalMethodChainUnused() {
        used();
    }

    private void used() {
    }

    private void unused2() { // violation, "Unused local method 'unused2'"
    }
}
