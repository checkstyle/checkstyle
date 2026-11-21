/*
OverloadMethodsDeclarationOrder
modifierGroups = (default)
orderByIncreasingParameterCount = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderModifierGroupsDefault {

    void foo() {
    }

    void bar() {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    public void foo(String str) {
    }

}
