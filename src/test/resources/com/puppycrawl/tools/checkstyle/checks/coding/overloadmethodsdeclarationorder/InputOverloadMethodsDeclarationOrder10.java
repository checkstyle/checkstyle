/*
OverloadMethodsDeclarationOrder
modifierGroups = al, c.

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder10 {
    final void foo() { } // ok
    final void bar() { } // ok
    synchronized void foo(int i) { } // ok
    synchronized void bar(int i) { } // ok
}
