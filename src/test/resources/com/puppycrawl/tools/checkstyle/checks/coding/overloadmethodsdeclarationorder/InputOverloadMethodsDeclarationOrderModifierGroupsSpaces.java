/*
OverloadMethodsDeclarationOrder
modifierGroups = public ,  static
orderByIncreasingParameterCount = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderModifierGroupsSpaces {

    static void foo() {
    }

    void bar() {
    }

    final void foo(String s) {
    }

    void baz() {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    static void foo(int i) {
    }

}
