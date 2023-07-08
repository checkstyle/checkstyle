/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = (default)

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck

*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolder8 {
    static final String unusedLocalVariableCheck
            = "UnusedLocalVariableCheck";

    void test() {
        @SuppressWarnings(unusedLocalVariableCheck)
        int a;
    }

    void test2() {
        @SuppressWarnings(InputSuppressWarningsHolder8.unusedLocalVariableCheck)
        int a;
    }
}
