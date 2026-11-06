/*
OverloadMethodsDeclarationOrder
modifierGroups = static, public
orderByIncreasingParameterCount = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderModifierGroupsNearest {

    public static void foo() {
    }

    public void foo(String s) {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    static void foo(int i) {
    }

    void bar() {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    public static void foo(double d) {
    }

}
