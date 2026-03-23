/*
RightCurlyAloneOrEmpty
tokens = ANNOTATION_DEF, ENUM_DEF, CLASS_DEF, METHOD_DEF

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

public class InputRightCurlyAloneOrEmptyMisc {
    public @interface TestAnnotation {}

    public @interface TestAnnotation1 { String someValue(); }
    // violation above '}' at column 61 should be alone on a line'

    public @interface TestAnnotation2 {
        String someValue(); }
    // violation above '}' at column 29 should be alone on a line'

    public @interface TestAnnotation3 {
        String someValue();
    }

    public @interface TestAnnotation4 { String someValue();
    }

    enum TestEnum2 {
        SOME_VALUE; } // violation '}' at column 21 should be alone on a line'

    enum TestEnum3 {}

    class temp {}
}
