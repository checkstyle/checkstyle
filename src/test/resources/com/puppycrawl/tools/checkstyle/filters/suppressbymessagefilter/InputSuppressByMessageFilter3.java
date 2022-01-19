/*
SuppressByMessageFilter
checkFormat = "RequireThisCheck"
messageFormat = (null)


com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck
checkFields = false
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.filters.suppressbymessagefilter;

public class InputSuppressByMessageFilter3 {
    private int a;
    private int b;
    private int c;

    public InputSuppressByMessageFilter3(int a) {
        a = a;
        b = 0; // ok
        foo(5);
    }

    public void foo(int c) {
        c = c;
    }
}
