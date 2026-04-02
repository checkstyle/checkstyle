/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldChainedDot {

    private String value = "hello";

    public int getLength() {
        return value.toUpperCase().length();
    }
}
