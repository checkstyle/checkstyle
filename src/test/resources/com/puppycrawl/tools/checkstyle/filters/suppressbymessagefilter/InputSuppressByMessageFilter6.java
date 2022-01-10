/*
com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck
checkFields = true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.filters.suppressbymessagefilter;

public class InputSuppressByMessageFilter6 {
    private int a;
    private int b;
    private int c;

    public InputSuppressByMessageFilter6(int a) {
        a = a; // violation
        b = 0; // violation
        foo(5);
    }

    public void foo(int c) {
        c = c; // violation
    }
}
