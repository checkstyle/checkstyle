//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

public class InputSuppressWarningsHolder5{

    public static Object foo1(@SuppressWarnings("unsused") Object str) {
        String myString = (@SuppressWarnings("unsused") String) str;
        Object object = new @SuppressWarnings("unsused") Object();
        try {
            return null;
            
        } catch (@SuppressWarnings("unsused") Exception ex) {
            return "";
        }
    }

    void foo2() throws @SuppressWarnings("unsused") Exception {  }

    public void foo3() {
        Map.@SuppressWarnings("unsused") Entry entry;
        MyObject myObject = new MyObject();
        myObject.<@SuppressWarnings("unsused") String>myMethod();
        myObject.new @SuppressWarnings("unsused") MyObject2();
    }

    public static <T> void foo4(Object str) {
        List<@SuppressWarnings("unsused") ? extends Comparable<T>> unchangeable;
    }

    abstract class UnmodifiableList<T>
    implements @SuppressWarnings("unsused") List<@SuppressWarnings("unsused") T> {
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
