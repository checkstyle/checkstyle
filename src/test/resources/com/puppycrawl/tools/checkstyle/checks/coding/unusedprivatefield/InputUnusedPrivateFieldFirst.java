/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldFirst {
    private int b; // ok, as b is used

    public void setB(int b) {
        this.b = b;
    }
}
