/*
UnusedLocalMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

public class InputUnusedLocalMethodOverload {
    public InputUnusedLocalMethodOverload() {
        used("1");
        used(1);
        unused("1");
    }

    private void used(String s) {
    }

    private void used(Integer s) {
    }

    private void unused(String s) {
    }

    private void unused(Integer s) { // violation, "Unused local method 'unused'"
    }
}
