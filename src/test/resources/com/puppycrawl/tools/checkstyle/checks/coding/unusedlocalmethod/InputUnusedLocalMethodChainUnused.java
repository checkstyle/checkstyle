/*
InputUnusedLocalMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

public class InputUnusedLocalMethodChainUnused {
    public InputUnusedLocalMethodChainUnused() {
        used();
    }

    private void used() {
        used2();
    }

    private void used2() {
    }

    private void unused2() { // violation, "Unused local method 'unused2'"
    }
}
