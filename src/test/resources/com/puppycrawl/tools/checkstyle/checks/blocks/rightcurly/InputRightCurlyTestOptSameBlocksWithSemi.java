/*
RightCurly
option = (default)SAME
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestOptSameBlocksWithSemi {

    public void testMethod() {}; // ok

    public void testMethod1() {
    }; // violation

    public class TestClass {}; // ok

    public class TestClass1 {
    }; // violation

    public class TestClass2 {
        public TestClass2() {}; // ok

        public TestClass2(String someValue) {
        }; // violation
    }

    public void testMethod11() {
    }
    ;

    public @interface TestAnnnotation5 {
        String someValue(); }; // violation

    public @interface TestAnnotation6 {}; // ok

    public @interface TestAnnotation7 {
        String someValue();
    }; // violation

    public @interface TestAnnotation8 { String someValue();
    }; // violation

    public @interface TestAnnotation9 { String someValue(); }; // ok

    enum TestEnum{}; // ok

    enum TestEnum1{
        SOME_VALUE;}; // violation

    enum TestEnum2 { SOME_VALUE; }; // ok

    enum TestEnum3{
        SOME_VALUE;
    }; // violation

    enum TestEnum4{ SOME_VALUE;
    }; // violation

    interface Interface1
    {
        int i = 1;
        public void meth1(); }; // violation

    interface Interface2
    { int i = 1; public void meth1(); };

    interface Interface3 {
        void display();
        interface Interface4 {
            void myMethod();
        };}; // 2 violations

    interface InterfaceEndingWithSemiColon2 {
        public void fooEmpty();
    }; // violation
}
