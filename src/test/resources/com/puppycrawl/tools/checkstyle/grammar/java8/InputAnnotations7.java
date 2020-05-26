package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


public class InputAnnotations7 {

    public static void main(String[] args) {
        Object object = new @Interned Object();

    }

    @Target(ElementType.TYPE_USE)
    @interface Interned {

    }

}
