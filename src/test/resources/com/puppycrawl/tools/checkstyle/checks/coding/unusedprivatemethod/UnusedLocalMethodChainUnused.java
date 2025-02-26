package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatemethod;

public class UnusedLocalMethodChainUnused {
    public UnusedLocalMethodChainUnused() {
        used();
    }
    private void used() {
        used2();
    }
    private void used2() {
    }
    private void unused2() {
    }
}
