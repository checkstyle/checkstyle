/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatemethod;

public class UnusedLocalMethodOverloadUnused {
    public UnusedLocalMethodOverloadUnused() {
        used("1");
    }
    public void used(String s) {
    }
    public void used(Integer s) {
    }
}
