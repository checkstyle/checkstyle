/*
 * Config:
 * option = same
 * tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ANNOTATION_DEF, ENUM_DEF
 */

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlySameBlocksWithSemi {

    public void testMethod() {};

    public void testMethod1() {
    }; //violation

    public class TestClass {};

    public class TestClass1 {
    }; //violation

    public class TestClass2 {
        public TestClass2() {};

        public TestClass2(String someValue) {
        }; //violation
    }

    public void testMethod11() {
    }
    ;

    public @interface TestAnnnotation5 {
        String someValue(); }; //violation

    public @interface TestAnnotation6 {};

    public @interface TestAnnotation7 {
        String someValue();
    }; //violation

    public @interface TestAnnotation8 { String someValue();
    }; //violation

    public @interface TestAnnotation9 { String someValue(); };

    enum TestEnum{};

    enum TestEnum1{
        SOME_VALUE;}; //violation

    enum TestEnum2 { SOME_VALUE; };

    enum TestEnum3{
        SOME_VALUE;
    }; //violation

    enum TestEnum4{ SOME_VALUE;
    }; //violation

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
        };}; // violaton  - for both of the right curly braces

    interface InterfaceEndingWithSemiColon2 {
        public void fooEmpty();
    }; // ok until #7541
}
