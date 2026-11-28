/*
OverloadMethodsDeclarationOrder
modifierGroups = public static, static, public
orderByIncreasingParameterCount = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderModifierGroupsOverlap {

    void foo() {
    }

    final void foo(String a, String b) {
    }

    public static void foo(String a) {
    }

    static public void foo(int a) {
    }

    public void foo(double t) {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    static void foo(float x) {
    }

    // violation below 'All overloaded methods should be placed next to each other.'
    public static void foo(byte b) {
    }

}
