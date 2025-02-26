/*
UnusedPrivateMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatemethod;

public class UnusedLocalMethodChain {
    public UnusedLocalMethodChain() {
        System.out.println(used2(1)); // call
    }

    private String used2(Integer i) {
        return i.toString();
    }

    private void unused() {
    }
}
