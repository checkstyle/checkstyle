package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface NonNull {

}

public class InputNoWhitespaceBeforeAnnotations {

    public void foo(final char @NonNull [] param) {}

    @NonNull int @NonNull[] @NonNull[] fiel1;
    @NonNull int @NonNull [] @NonNull [] field2;
    //@NonNull int @NonNull ... field3; // non-compilable
    //@NonNull int @NonNull... field4; // non-compilable


    public void foo1(final char[] param) {
    }

    public void foo2(final char [] param) {
    }

    public void foo3(final char @NonNull[] param) {
    }

    public void foo4(final char @NonNull [] param) {
    }

    void test1(String... param) {
    }

    void test2(String ... param) { // violation on 8.32
    }

    void test3(String @NonNull... param) {
    }

    void test4(String @NonNull ... param) { // violation on 8.32
    }
}
