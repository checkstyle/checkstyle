/*
WhitespaceAfter
tokens = COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, DO_WHILE, ANNOTATIONS


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;

public class InputWhitespaceAfterAnnotation {
    @Target(ElementType.TYPE_USE)
    @interface Size {
        int max();
    }

    @Target(ElementType.TYPE_USE)
    @interface AnnoType {}

    @Target(ElementType.TYPE_USE)
    @interface NonNull2 {}

    @Target(ElementType.TYPE_USE)
    @interface NonNull {}

    @NonNull int @AnnoType[] @NonNull2[] field1;
    // 2 violations above:
    //   ''AnnoType' is not followed by whitespace'
    //   ''NonNull2' is not followed by whitespace'
    @NonNull int @AnnoType [] @NonNull2 [] field2;

    void test1(String @NonNull... param) {}
    // violation above ''NonNull' is not followed by whitespace'

    void test2(String @NonNull ... param) {}

    void test3(String @NonNull[]... param) {}
    // violation above ''NonNull' is not followed by whitespace'

    void test4(String @AnnoType []... param) {}

    void test5(String @Size(max = 10)... names) {}
    // violation above '')' is not followed by whitespace'

    void test6(String @Size(max = 10) ... names) {}

    public String @NonNull[] @AnnoType[] test7() {
        // 2 violations above:
        //   ''NonNull' is not followed by whitespace'
        //   ''AnnoType' is not followed by whitespace'
        return null;
    }

    public String @NonNull2 [] @AnnoType [] test8() {
        return null;
    }

    public void test9(final char @AnnotationAfterTest[] a) {}
    // violation above ''AnnotationAfterTest' is not followed by whitespace'

    public void test10(final char @AnnotationAfterTest [] a) {}

    public void test11(final char @AnnotationAfterTest []a) {}

    public void test12(final char @AnnotationAfterTest[]a) {}
    // violation above ''AnnotationAfterTest' is not followed by whitespace'

    public @AnnotationAfterTest String @AnnoType [] test9() {
        return new @NonNull2 String @AnnotationAfterTest[3];
        // violation above ''AnnotationAfterTest' is not followed by whitespace'
    }

    public record Example(char @AnnotationAfterTest[] data) {}
    // violation above ''AnnotationAfterTest' is not followed by whitespace'

    public record Example1(
        List<@NonNull String> names,
        @NonNull String value,
        String @AnnotationAfterTest[] array
        // violation above ''AnnotationAfterTest' is not followed by whitespace'
    ) {}

    class ACls extends @Tacos.Type AKls<@Tacos.Type String>.@Tacos.Type BClas<@Tacos.Type Number> {

        public ACls(final AKls<String> enclosingInstance) {
            enclosingInstance.super();
        }
    }

    class AKls<T> {
        class BClas<U> {}
    }

    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    @interface AnnotationAfterTest {}
}

class Tacos {
    @Target({ElementType.TYPE_USE})
    public @interface Type {}
}
