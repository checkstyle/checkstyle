package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;


public class InputSuppressWarningsHolder5{

    public static Object foo1(@SuppressWarnings("unused") Object str) {
        String myString = (@SuppressWarnings("unused") String) str;
        Object object = new @SuppressWarnings("unused") Object();
        try {
            return null;

        } catch (@SuppressWarnings("unused") Exception ex) {
            return "";
        }
    }

    void foo2() throws @SuppressWarnings("unused") Exception {  }

    public void foo3() {
        Map.@SuppressWarnings("unused") Entry entry;
        MyObject myObject = new MyObject();
        myObject.<@SuppressWarnings("unused") String>myMethod();
        myObject.new @SuppressWarnings("unused") MyObject2();
    }

    public static <T> void foo4(Object str) {
        List<@SuppressWarnings("unused") ? extends Comparable<T>> unchangeable;
    }

    abstract class UnmodifiableList<T>
    implements @SuppressWarnings("unused") List<@SuppressWarnings("unused") T> {
    }

    class MyObject{

        public void myMethod(){};

        class MyObject2{}
    }

    @Target(ElementType.TYPE_USE)
    @interface SuppressWarnings {
        String value();
    }
}
