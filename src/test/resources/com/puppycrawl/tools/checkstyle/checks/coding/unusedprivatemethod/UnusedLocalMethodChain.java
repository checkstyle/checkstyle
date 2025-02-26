/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatemethod;

public class UnusedLocalMethodChain {
    public UnusedLocalMethodChain() {
        used();
    }
    public void used() {
        used2();
    }
    public void used2() {
    }
}
