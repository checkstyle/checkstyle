public class InputClassWithPrivateCtorWithNestedExtendingClassWithoutPackage {
    class A {
        private A() {}
        private class ExtendA extends A {}
    }

    class C {
        private C() {}
        private class ExtendC extends com.nonexistent.packages.C {}
    }
}
