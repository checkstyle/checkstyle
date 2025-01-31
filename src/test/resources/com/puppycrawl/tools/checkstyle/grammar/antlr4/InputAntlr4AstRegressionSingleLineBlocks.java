package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionSingleLineBlocks {

    public void testMethod() {};  

    public void testMethod1() {
    }; // violation

    public class TestClass {};  

    public class TestClass1 {
    }; // violation

    public class TestClass2 {
        public TestClass2() {};  

        public TestClass2(String someValue) {
        }; // violation
    }

    public void testMethod11() {
    }
    ;

    public @interface TestAnnnotation5 {
        String someValue(); }; // violation

    public @interface TestAnnotation6 {};  

    public @interface TestAnnotation7 {
        String someValue();
    }; // violation

    public @interface TestAnnotation8 { String someValue();
    }; // violation

    public @interface TestAnnotation9 { String someValue(); };  

    enum TestEnum{};  

    enum TestEnum1{
        SOME_VALUE;}; // violation

    enum TestEnum2 { SOME_VALUE; };  

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
