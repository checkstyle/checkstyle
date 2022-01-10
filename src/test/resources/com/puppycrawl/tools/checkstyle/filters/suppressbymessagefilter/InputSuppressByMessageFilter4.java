/*
com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck
checkFields = true
checkMethods = true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.filters.suppressbymessagefilter;

public class InputSuppressByMessageFilter4 {
    private int a;
    private int b;
    private int c;

    public InputSuppressByMessageFilter4(int a) {
        a = a; // violation
        b = 0; // violation
        foo(5); // violation
    }

    public void foo(int c) {
        c = c; // violation
    }
}
