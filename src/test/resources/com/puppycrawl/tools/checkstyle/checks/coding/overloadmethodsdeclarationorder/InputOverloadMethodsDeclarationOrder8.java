/*
OverloadMethodsDeclarationOrder
modifierGroups = ^protected, package, private

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

import java.nio.file.Path;

class InputOverloadMethodsDeclarationOrder8 {
    void foo(String baz) { }

    protected void foo() { }

    //violation below 'All overloaded methods should be placed next to each other'
    static void foo(int i) { }

    //violation below 'All overloaded methods should be placed next to each other'
    protected void foo(byte b) { }

    private void foo(long l) { }
    //violation below 'All overloaded methods should be placed next to each other'
    void foo(Path p) { }
}
