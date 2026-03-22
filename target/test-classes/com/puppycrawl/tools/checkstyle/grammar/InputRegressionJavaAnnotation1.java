package com.puppycrawl.tools.checkstyle.grammar;

@interface InputRegressionJavaAnnotation1 {
    int f1 = 0;

    String[] m1();
    String[] m2() default {};

    class SomeClass {
        private SomeClass() {}
    }
}
@interface ComplexAnnotation {
    InputRegressionJavaAnnotation1[] value();
}
