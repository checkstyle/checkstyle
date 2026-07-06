/*
UnnecessarySemicolonInEnumeration


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicoloninenumeration;

enum WithAbstractMethod {
    A() {
        @Override
        public void method() {}
    },
    B() {
        @Override
        public void method() {}
    };
    ;  // violation 'Unnecessary semicolon'
    public abstract void method();
}
