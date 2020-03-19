package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface NonNull {

}

public class InputNoWhitespaceBeforeAnnotationsAndEllipsis {
    void test1(String @NonNull ... param) {} // warn

    void test2(String @NonNull... param) {}
}
