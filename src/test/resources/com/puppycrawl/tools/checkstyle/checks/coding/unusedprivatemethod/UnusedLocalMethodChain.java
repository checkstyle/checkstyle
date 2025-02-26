/*
UnusedPrivateMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatemethod;

public class UnusedLocalMethodChain {
    public UnusedLocalMethodChain() {
        used();
    }

    private void used() {
        used2();
    }

    private void used2() {
    }
}
