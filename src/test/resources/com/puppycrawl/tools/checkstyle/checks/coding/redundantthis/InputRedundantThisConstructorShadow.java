/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisConstructorShadow {
    private String a;

    public InputRedundantThisConstructorShadow(String a, String b, String[] c) {
        this.a = a;
    }
}
