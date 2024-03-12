/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = (default)

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck

com.puppycrawl.tools.checkstyle.checks.naming.LocalVariableNameCheck
format = ^[a-z][_a-zA-Z0-9]{2,}$


*/

//non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolder2 {
    static final String unusedLocalVariableCheck
            = "UnusedLocalVariableCheck";
    static final String localVariableNameCheck
            = "LocalVariableNameCheck";

    void test2() {
        @SuppressWarnings({InputSuppressWarningsHolder2.unusedLocalVariableCheck,
                InputSuppressWarningsHolder2.localVariableNameCheck})
        int a;
    }

    void test3() {
        @SuppressWarnings({unusedLocalVariableCheck, localVariableNameCheck})
        int a;
    }

    void test1() {
        @SuppressWarnings(InputSuppressWarningsHolder2.localVariableNameCheck)
        int a; // violation 'Unused local variable 'a''
    }

    void test4() {
        @SuppressWarnings(localVariableNameCheck)
        int a; // violation 'Unused local variable 'a''
    }
}
