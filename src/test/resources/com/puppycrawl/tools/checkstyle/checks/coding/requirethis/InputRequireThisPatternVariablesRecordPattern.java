/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisPatternVariablesRecordPattern {
    private String s;
    private int x;
    private int z;

    record R(String s, int x, int z) { }

    public void test(Object obj) {
        if (!(obj instanceof R(String s, int x, int z))) {
            s = "a"; // violation 'Reference to instance variable 's' needs "this."\.'
            x = 1; // violation 'Reference to instance variable 'x' needs "this."\.'
            z = 2; // violation 'Reference to instance variable 'z' needs "this."\.'
        }
        else {
            s = "b";
            x = 3;
            z = 4;
        }
    }
}
