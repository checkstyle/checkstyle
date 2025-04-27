/*
UnusedLocalMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

public class InputUnusedLocalMethodMethodOverload {
    public InputUnusedLocalMethodMethodOverload() {
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

    private void unused(Integer s) { // ok as overload not supported https://github.com/checkstyle/checkstyle/issues/16375#issuecomment-2671577546
    }
}
