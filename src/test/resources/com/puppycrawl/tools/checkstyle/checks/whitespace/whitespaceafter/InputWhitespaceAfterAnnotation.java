/*
WhitespaceAfter
tokens = COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, DO_WHILE, ANNOTATIONS


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputWhitespaceAfterAnnotation {
    @Target(ElementType.TYPE_USE)
    @interface Size {
        int max();
    }

    @Target(ElementType.TYPE_USE)
    @interface NonNull {}
    @NonNull int @NonNull[] @NonNull[] field1;
    // 2 violations above:
    //   ''NonNull' is not followed by whitespace'
    //   ''NonNull' is not followed by whitespace'
    @NonNull int @NonNull [] @NonNull [] field2;

    void test1(String @NonNull... param) {}
    // violation above ''NonNull' is not followed by whitespace'

    void test2(String @NonNull ... param) {}

    void test3(String @NonNull[] ... param) {} // f-n
    // violation above ''NonNull' is not followed by whitespace'

    void test4(String @NonNull [] ... param) {}

    void test5(String @Size(max = 10)... names) {} // fn
    // violation above '')' is not followed by whitespace'

    void test6(String @Size(max = 10) ... names) {}

    public String @NonNull[] @NonNull[] test7() {
        // 2 violations above:
        //   ''NonNull' is not followed by whitespace'
        //   ''NonNull' is not followed by whitespace'
        return null;
    }

    public String @NonNull [] @NonNull [] test8() {
        return null;
    }

    public void test9(final char @AnnotationAfterTest[] a) {}
    // violation above ''AnnotationAfterTest' is not followed by whitespace'

    public void test10(final char @AnnotationAfterTest [] a) {}

    public void test11(final char @AnnotationAfterTest []a) {}

    public @AnnotationAfterTest String @AnnotationAfterTest [] test9() {
        return new @AnnotationAfterTest String @AnnotationAfterTest[3];
        // violation above ''AnnotationAfterTest' is not followed by whitespace'
    }

    @Target(ElementType.TYPE_USE)
    @interface AnnotationAfterTest {}
}
