/*
UnusedPrivateMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatemethod;

public class UnusedLocalMethodChain {
    public UnusedLocalMethodChain() {
        used2();
    }

    private void used2() {
    }

    private void unused() { // violation, "Unused local method 'unused'"
    }
}
