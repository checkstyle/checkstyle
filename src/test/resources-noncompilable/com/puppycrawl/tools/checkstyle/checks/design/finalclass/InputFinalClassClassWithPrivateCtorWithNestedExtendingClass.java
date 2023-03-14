/*
FinalClass


*/

//non-compiled with javac: reference to non existen package "com.nonexistent.packages.C" for testing
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassClassWithPrivateCtorWithNestedExtendingClass {
    class A {
        private A() {}
        private class ExtendA extends A {}
    }

    class B {
        private B() {}
        private class ExtendB extends com.puppycrawl.tools.checkstyle.checks.design.finalclass.
            InputFinalClassClassWithPrivateCtorWithNestedExtendingClass.B {}
    }

    class C { // violation
        private C() {}
        private class ExtendC extends com.nonexistent.packages.C {}
    }
}
