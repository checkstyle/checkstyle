package com.puppycrawl.tools.checkstyle.grammar.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


public class InputAnnotations10 {
    public static Object methodName(Object str) {
        try {
            return null;

        } catch (@MyAnnotation1(name = "ABC", version = 1) Exception ex) {
            return "";
        }
    }

    @Target(ElementType.TYPE_USE)
    @interface MyAnnotation1 {

    String name();
    int version();
    }
}
