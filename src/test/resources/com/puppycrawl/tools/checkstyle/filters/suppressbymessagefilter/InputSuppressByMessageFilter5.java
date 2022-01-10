/*
SuppressByMessageFilter
checkFormat = "RequireThisCheck"
messageFormat = "null"


com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck
checkFields = false
checkMethods = true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.filters.suppressbymessagefilter;

public class InputSuppressByMessageFilter5 {
    private int a;
    private int b;
    private int c;

    public InputSuppressByMessageFilter5(int a) {
        a = a;
        b = 0;
        foo(5); // violation
    }

    public void foo(int c) {
        c = c;
    }
}
