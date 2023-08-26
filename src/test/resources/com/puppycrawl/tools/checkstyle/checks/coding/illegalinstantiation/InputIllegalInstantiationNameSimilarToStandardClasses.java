/*
IllegalInstantiation
classes = java.gang.foo
tokens = LITERAL_NEW,PACKAGE_DEF,IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

public class InputIllegalInstantiationNameSimilarToStandardClasses {
    void method() {
        new foo();
    }

    class foo {
    }
}
