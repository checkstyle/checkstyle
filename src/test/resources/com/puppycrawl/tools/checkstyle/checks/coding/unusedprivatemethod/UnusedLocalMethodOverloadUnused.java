/*
UnusedPrivateMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatemethod;

public class UnusedLocalMethodOverloadUnused {
    public UnusedLocalMethodOverloadUnused() {
        used("1");
    }

    private void used(String s) {
    }

    private void used(Integer s) {
    }
}
