package com.puppycrawl.tools.checkstyle.checks.design;

public class InputClassWithPrivateCtorWithNestedExtendingClass {
    class A {
        private A() {}
        private class ExtendA extends A {}
    }

    class B {
        private B() {}
        private class ExtendB extends
                com.puppycrawl.tools.checkstyle.checks.design.InputClassWithPrivateCtorWithNestedExtendingClass.B {}
    }

    class C {
        private C() {}
        private class ExtendC extends com.nonexistent.packages.C {}
    }
}
