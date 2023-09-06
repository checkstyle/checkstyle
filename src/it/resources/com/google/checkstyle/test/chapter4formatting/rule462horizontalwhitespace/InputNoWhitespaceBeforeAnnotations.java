package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface NonNull {

}

public class InputNoWhitespaceBeforeAnnotations {

    public void foo(final char @NonNull [] param) {} 
    @NonNull int @NonNull[] @NonNull[] fiel1; // ok until #8205
    @NonNull int @NonNull [] @NonNull [] field2; 
    //@NonNull int @NonNull ... field3; // non-compilable
    //@NonNull int @NonNull... field4; // non-compilable


    public void foo1(final char[] param) { 
    }

    public void foo2(final char [] param) { 
    }

    public void foo3(final char @NonNull[] param) { // ok until #8205
    }

    public void foo4(final char @NonNull [] param) { 
    }

    void test1(String... param) { // ok until #8205
    }

    void test2(String ... param) { // ok until #8205
    }

    void test3(String @NonNull... param) { // ok until #8205
    }

    void test4(String @NonNull ... param) { 
    }
}
