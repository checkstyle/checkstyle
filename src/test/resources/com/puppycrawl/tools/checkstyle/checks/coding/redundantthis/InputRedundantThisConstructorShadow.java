/*
RedundantThis
checkMethodCall=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisConstructorShadow {
    private String a;
    private String b;
    private String[] c;

    public InputRedundantThisConstructorShadow(String a, String b) {
        this.a = a; // ok, param 'a' shadows field
        this.b = b; // ok, param 'b' shadows field
    }

    public InputRedundantThisConstructorShadow(String a, String b, String[] c) {
        if (a.length() == 1) {
            this.a = a; // ok, param 'a' shadows field
            this.b = b; // ok, param 'b' shadows field
            this.c = c; // ok, param 'c' shadows field
        } else {
            this.a = a.substring(1); // ok, param 'a' shadows field
            this.b = "123";
            this.c = null;
        }
    }
}
