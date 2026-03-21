/*
RightCurly
option = ALONE
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestOptAloneBlocksWithSemi {

    public void testMethod() {}; // violation ''}' at column 31 should be alone on a line'

    public void testMethod1() {
    }; // violation ''}' at column 5 should be alone on a line'

    public class TestClass {}; // violation ''}' at column 29 should be alone on a line'

    public class TestClass1 {
    }; // violation ''}' at column 5 should be alone on a line'

    public class TestClass2 {
        public TestClass2() {}; // violation ''}' at column 30 should be alone on a line'

        public TestClass2(String someValue) {
        }; // violation ''}' at column 9 should be alone on a line'
    }

    public void testMethod11() {
    }
    ;

    public @interface TestAnnnotation5 {
        String someValue(); }; // violation ''}' at column 29 should be alone on a line'

    public @interface TestAnnotation6 {}; // violation ''}' at column 40 should be alone on a line'

    public @interface TestAnnotation7 {
        String someValue();
    }; // violation ''}' at column 5 should be alone on a line'

    public @interface TestAnnotation8 { String someValue();
    }; // violation ''}' at column 5 should be alone on a line'
    // violation below ''}' at column 61 should be alone on a line'
    public @interface TestAnnotation9 { String someValue(); };

    enum TestEnum{}; // violation ''}' at column 19 should be alone on a line'

    enum TestEnum1{
        SOME_VALUE;}; // violation ''}' at column 20 should be alone on a line'

    enum TestEnum2 { SOME_VALUE; }; // violation ''}' at column 34 should be alone on a line'

    enum TestEnum3{
        SOME_VALUE;
    }; // violation ''}' at column 5 should be alone on a line'

    enum TestEnum4{ SOME_VALUE;
    }; // violation ''}' at column 5 should be alone on a line'

    interface Interface1
    {
        int i = 1;
        public void meth1(); }; // violation ''}' at column 30 should be alone on a line'

    interface Interface3 {
        void display();
        interface Interface4 {
            void myMethod();
        };}; // 2 violations

    interface InterfaceEndingWithSemiColon2 {
        public void fooEmpty();
    }; // violation ''}' at column 5 should be alone on a line'
}
