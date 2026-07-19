/*
IllegalInstantiation
classes = java.gang.foo

*/



package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

public class InputIllegalInstantiationNameSimilarToStandardClasses {
    void method() {
        new foo();
    }

    class foo {
    }
}
