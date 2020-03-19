package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface NonNull {

}

@Target(ElementType.TYPE_USE)
@interface C {

}

@Target(ElementType.TYPE_USE)
@interface A {

}

@Target(ElementType.TYPE_USE)
@interface B {

}

public class InputNoWhitespaceBeforeAnnotations {
    @C int @A [] @B [] f;

    public void foo(final char @NonNull [] param) {}

    void test1(String @NonNull ... param) {}

    void test2(String @NonNull... param) {}
}
