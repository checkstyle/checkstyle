/*
OverloadMethodsDeclarationOrder
modifierGroups = ^protected, package, private

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

import java.nio.file.Path;

class InputOverloadMethodsDeclarationOrder8 {
    void foo(String baz) { } // ok

    protected void foo() { } // ok

    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    static void foo(int i) { }

    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    protected void foo(byte b) { }

    private void foo(long l) { }
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    void foo(Path p) { }
}
