/*
OverloadMethodsDeclarationOrder
modifierGroups = static
orderByIncreasingParameterCount = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public record InputOverloadMethodsDeclarationOrderModifierGroupsRecord() {

    void foo() {
    }

    static void bar() {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    void foo(String s) {
    }

    static void foo(int i) {
    }

}
