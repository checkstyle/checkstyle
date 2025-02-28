/*
InputUnusedLocalMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

public class InputUnusedLocalMethodChain {
    public InputUnusedLocalMethodChain() {
        used2();
    }

    private void used2() {
    }

    private void unused() { // violation, "Unused local method 'unused'"
    }
}
