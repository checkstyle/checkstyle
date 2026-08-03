/*
RedundantThis
checkMethods = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisPatternVariables {
    private String s;

    public void method(Object obj) {
        if (obj instanceof String s) {
            System.out.println(this.s);
        }
    }

    public void method2(Object obj) {
        if (!(obj instanceof String s)) {
            return;
        }
        System.out.println(this.s);
    }

    public void method3(Object obj) {
        switch (obj) {
            case String s -> System.out.println(this.s);
            default -> {}
        }
    }
}
