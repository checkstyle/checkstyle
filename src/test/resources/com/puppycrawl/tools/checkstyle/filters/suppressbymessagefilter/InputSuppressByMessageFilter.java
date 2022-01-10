package com.puppycrawl.tools.checkstyle.filters.suppressbymessagefilter;

/*
checkFields = "true"
checkMethods = "true"
validateOnlyOverlapping = "false"
*/
public class InputSuppressByMessageFilter {
    private int a;
    private int b;
    private int c;

    public InputSuppressByMessageFilter(int a) {
        a = a; // violation
        b = 0; // violation
        foo(5); // violation
    }

    public void foo(int c) {
        c = c; // violation
    }
}