package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputAnnotationLocation4 {
    @Deprecated // <--class, separate line
    public class Annotation
    {
        @Deprecated // <--method, separate line
        public void test(@MyAnnotation String s) { // <--parameter, same line
            @MyAnnotation // <--variable, separate line
            Integer i;
            for (@MyAnnotation char c : s.toCharArray()) { // <--variable in for each, same line
            }
        }
    }

    public class Test {
        public void foo1() {
            try {
                // some code
            }
            catch (@MyAnnotation Exception ex) {

            }
        }

        public void foo2() {
            for (@MyAnnotation int i = 0; i < 10; i++) {

            }
        }

        public void foo3() {
            MathOperation c = (@MyAnnotation int a, @MyAnnotation int b) -> a + b;
        }

        public void foo4(@MyAnnotation int a, @MyAnnotation int b) {}

        public void foo5(@SuppressWarnings("unchecked") int a) {}
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    @Target(ElementType.TYPE_USE)
    public @interface MyAnnotation {}
}
