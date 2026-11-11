/*
RightCurly
option = \tALONE
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyTestWithAnnotations4
{
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

    public @interface TestAnnotation {} // violation ''}' at column 39 should be alone on a line'
    // violation below ''}' at column 56 should be alone on a line'
    public @interface TestAnnotation1{ String value(); }

    public @interface TestAnnotation2 {
        String value();} // violation ''}' at column 24 should be alone on a line'

    public @interface TestAnnotation3 {
        String value();
    }

    public @interface TestAnnotation4 { String value();
    }

    public @interface TestAnnotation5 {
        String someValue(); }; // violation ''}' at column 29 should be alone on a line'

    public @interface TestAnnotation6 {}; // violation ''}' at column 40 should be alone on a line'

    public @interface TestAnnotation7 {
        String someValue();
    }; // violation ''}' at column 5 should be alone on a line'

    public @interface TestAnnotation8 { String someValue();
    }; // violation ''}' at column 5 should be alone on a line'

    public @interface TestAnnotation9 { String someValue(); };
    // violation above ''}' at column 61 should be alone on a line'
}
