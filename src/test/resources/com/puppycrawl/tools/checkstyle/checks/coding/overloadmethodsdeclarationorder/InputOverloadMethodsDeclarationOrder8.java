/*
OverloadMethodsDeclarationOrder
modifierGroups = ^protected, package, private

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

import java.nio.file.Path;

class InputOverloadMethodsDeclarationOrder8 {
    void foo(String baz) { } // ok

    protected void foo() { } // ok

    static void foo(int i) { } // violation

    protected void foo(byte b) { } // violation

    private void foo(long l) { } // ok

    void foo(Path p) { } // violation
}
