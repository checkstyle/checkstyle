/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldTwo {

    private int c; // ok, as c is used

    public int getC() {
        return c;
    }
}
