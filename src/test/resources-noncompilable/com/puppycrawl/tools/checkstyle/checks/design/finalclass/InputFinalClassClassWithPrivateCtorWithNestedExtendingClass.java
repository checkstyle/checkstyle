/*
FinalClass


*/

//non-compiled with javac: reference to non existen package "com.nonexistent.packages.C" for testing
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassClassWithPrivateCtorWithNestedExtendingClass {
    class A {
        private A() {}
        private class ExtendA extends A {}
    } // violation above 'Class ExtendA should be declared as final'

    class B {
        private B() {} // violation below 'Class ExtendB should be declared as final'
        private class ExtendB extends com.puppycrawl.tools.checkstyle.checks.design.finalclass.
            InputFinalClassClassWithPrivateCtorWithNestedExtendingClass.B {}
    }

    class C { // violation 'Class C should be declared as final'
        private C() {}
        private class ExtendC extends com.nonexistent.packages.C {}
    } // violation above 'Class ExtendC should be declared as final'
}
