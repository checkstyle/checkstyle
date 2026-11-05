/*
OverloadMethodsDeclarationOrder
modifierGroups = static , public static
orderByIncreasingParameterCount = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderModifierGroupsSpaces {

    static void foo() {
    }

    public void foo(String s) {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    static void foo(int i) {
    }

}
