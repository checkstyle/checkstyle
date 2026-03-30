/*
RedundantThis
checkMethods = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisPatternVariables {
    private String s;
    public void test(Object obj) {
        if (obj instanceof String s) {
            System.out.println(this.s);
        }
    }

    public void test2(Object obj) {
        switch (obj) {
            case String s -> System.out.println(this.s);
            default -> {}
        }
    }
}
