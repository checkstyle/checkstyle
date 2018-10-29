//non-compiled with javac: reference to non existen package "com.nonexistent.packages.C" for testing
public class InputFinalClassClassWithPrivateCtorWithNestedExtendingClassWithoutPackage {
    class A {
        private A() {}
        private class ExtendA extends A {}
    }

    class C {
        private C() {}
        private class ExtendC extends com.nonexistent.packages.C {}
    }
}
