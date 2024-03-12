/*
FinalClass


*/

//non-compiled with javac: reference to non existen package "com.nonexistent.packages.C" for testing
public class InputFinalClassClassWithPrivateCtorWithNestedExtendingClassWithoutPackage {
    class A {
        private A() {}
        private class ExtendA extends A {}
    } // violation above 'Class ExtendA should be declared as final'

    class C { // violation 'Class C should be declared as final'
        private C() {}
        private class ExtendC extends com.nonexistent.packages.C {}
    } // violation above 'Class ExtendC should be declared as final'
}
