/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = (default)

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck

*/

//non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolder1 {
    static final String unusedLocalVariableCheck
            = "UnusedLocalVariableCheck";

    void test() {
        @SuppressWarnings(unusedLocalVariableCheck)
        int a;
    }

    void test2() {
        @SuppressWarnings(InputSuppressWarningsHolder1.unusedLocalVariableCheck)
        int a;
    }
}
