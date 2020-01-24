//start line index in expected file is 2
package com.puppycrawl.tools.checkstyle.grammar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InputRegressionJava8Class2 {
    static class Inner1 { static class Inner2<V> { public void m() {} } }
    static class Inner3<T> { public void m() {} }

    public void m1(@MyAnnotation String @MyAnnotation ... vararg) {}
    public String m2() @MyAnnotation [] @MyAnnotation [] { return null; }    

    public void instructions() {
        // annotations
        Map.@MyAnnotation Entry e;
        String str = (@MyAnnotation String) "";
        (new Inner3()).<@MyAnnotation String>m();
        Object arr = new @MyAnnotation String @MyAnnotation [3];
        for (String a @MyAnnotation [] : m2()) {}
        Object arr2 = new @MyAnnotation int[3];        
    }
}

@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.TYPE_USE })
@interface MyAnnotation {
}
