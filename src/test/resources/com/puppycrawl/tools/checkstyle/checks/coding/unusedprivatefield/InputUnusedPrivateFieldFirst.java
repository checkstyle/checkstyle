/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.io.Serial;

public class InputUnusedPrivateFieldFirst {
    private int b; // ok, private field is used

    public void setB(int b) {
        this.b = b;
    }

    @Serial
    private static final long serialVersionUID = 1434589190483306227L;

}
