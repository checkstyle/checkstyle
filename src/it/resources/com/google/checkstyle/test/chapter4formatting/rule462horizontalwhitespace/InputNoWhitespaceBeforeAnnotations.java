package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface NonNull {

}

public class InputNoWhitespaceBeforeAnnotations {

    public void foo(final char @NonNull [] param) {} // OK

    @NonNull int @NonNull[] @NonNull[] fiel1; // OK until #8205
    @NonNull int @NonNull [] @NonNull [] field2; // OK
    //@NonNull int @NonNull ... field3; // non-compilable
    //@NonNull int @NonNull... field4; // non-compilable


    public void foo1(final char[] param) { // OK until #8205
    }

    public void foo2(final char [] param) { // OK
    }

    public void foo3(final char @NonNull[] param) { // OK until #8205
    }

    public void foo4(final char @NonNull [] param) { // OK
    }

    void test1(String... param) { // OK until #8205
    }

    void test2(String ... param) { // violation on 8.32
    }

    void test3(String @NonNull... param) { // OK until #8205
    }

    void test4(String @NonNull ... param) { // violation on 8.32
    }
}
