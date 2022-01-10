/*
com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck
checkFields = false
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.filters.suppressbymessagefilter;

public class InputSuppressByMessageFilter8 {
    private int a;
    private int b;
    private int c;

    public InputSuppressByMessageFilter8(int a) {
        a = a;
        b = 0; // ok
        foo(5); // ok
    }

    public void foo(int c) {
        c = c;
    }
}
