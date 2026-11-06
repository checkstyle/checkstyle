/*
OverloadMethodsDeclarationOrder
modifierGroups = public, private, static
orderByIncreasingParameterCount = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderModifierGroupsMultiple {

    private void foo() {
    }

    void bar() {
    }

    public void foo(String s) {
    }

    public void foo(String s, int i) {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    private static void foo(int i) {
    }

    private final void foo(double d) {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    static void foo(long l) {
    }

}
