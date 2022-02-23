/*
ParameterNumber
max = (default)7
ignoreOverriddenMethods = (default)false
ignoreMethodsAnnotatedBy = (default)
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

public class InputParameterNumberAnnotations
{
    void myMethod1(int a, int b, int c, int d, int e, int f, int g, int h) {// violation

    }

    @TestAnnotation1
    void myMethod2(int a, int b, int c, int d, int e, int f, int g, int h) {// violation

    }

    @TestAnnotation2(someValue = "any value")
    void myMethod3(int a, int b, int c, int d, int e, int f, int g, int h) {// violation

    }

    @TestAnnotation3(someValue = "some value")
    void myMethod4(int a, int b, int c, int d, int e, int f, int g, int h) {// violation

    }

    @interface TestAnnotation1 {
    }

    @interface TestAnnotation2 {
        String someValue();
    }

    @interface TestAnnotation3 {
        String someValue();
    }
}
