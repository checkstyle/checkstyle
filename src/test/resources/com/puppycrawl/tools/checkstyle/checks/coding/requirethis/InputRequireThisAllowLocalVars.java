/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

class InputRequireThisAllowLocalVars { // ok

    private String s1 = "foo1";
    String s2 = "foo2";

    InputRequireThisAllowLocalVars() {
        s1 = "bar1"; // violation
        String s2;
        s2 = "bar2"; // No violation. Local var allowed.
    }

    public int getS1() {
        String s1 = null;
        s1 = "bar"; // No violation
        s1 = s1; // violation
        return 1;
    }

    public String getS1(String param) {
        String s1 = null;
        s1 = param; // No violation
        s1 += s1;   // No violation. s1 is being returned.
        return s1;  // No violation
    }

    String getS2() {
        String s2 = null;
        s2+=s2; // violation
        return "return";
    }

    String getS2(String s2) {
        s2 = null; // violation
        return s2; // No violation. param is returned.
    }

    String getS2(int a) {
        String s2 = " ";
        s2 += s2; // violation
        return s1; // violation
    }
}
