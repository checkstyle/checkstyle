/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatemethod;

public class UnusedLocalMethodChainUnused {
    public UnusedLocalMethodChainUnused() {
        used();
    }
    public void used() {
        used2();
    }
    public void used2() {
    }
    public void unused2() {
    }
}
