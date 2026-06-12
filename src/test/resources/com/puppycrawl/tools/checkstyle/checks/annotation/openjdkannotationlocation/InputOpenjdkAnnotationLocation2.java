/*
OpenjdkAnnotationLocation
tokens = ANNOTATION_DEF, ANNOTATION_FIELD_DEF, METHOD_DEF, CLASS_DEF, VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

@MyAnnotation11 @MyAnnotation12 @MyAnnotation13
public class InputOpenjdkAnnotationLocation2 {

    @MyAnnotation13
    void method() {
    }

    @MyAnnotation13
    @MyAnnotation12
    void method2() {
    }

    @MyAnnotation13 @MyAnnotation12 void method3() {}

    @MyAnnotation11
    @MyAnnotation12 @MyAnnotation13
    @MyAnnotation14
    void method4() {
    // violation above 'Annotations on 'method4' must be all on one line or all on separate lines.'
    }

}

@MyAnnotation11 @MyAnnotation12 @MyAnnotation13
class TestClassGoodOne {
}

@MyAnnotation11
@MyAnnotation12
@MyAnnotation13
class TestClassGoodTwo {
}

@MyAnnotation11
@MyAnnotation12 @MyAnnotation13
class TestClassBad {
// violation above """Annotations on 'TestClassBad' must be all on one line or
// all on separate lines."""

    @MyAnnotation11 @MyAnnotation12
    @MyAnnotation13 int a;
    // 2 violations above:
    //  'Annotations must be on a separate line from 'a'.'
    //  'Annotations on 'a' must be all on one line or all on separate lines.'

    @MyAnnotation11 @MyAnnotation12 @MyAnnotation13 int b;

    @MyAnnotation11 @MyAnnotation12 @MyAnnotation13
    int c;

    @MyAnnotation11
    @MyAnnotation12
    @MyAnnotation13
    int d;

    // violation below 'Annotations must be on a separate line from 'e'.'
    @MyAnnotation11 public int
            e = 10;

    // violation below 'Annotations must be on a separate line from 'methodNotSingleLineBad'.'
    @MyAnnotation11 public String methodNotSingleLineBad() {
        return "";
    }

    @MyAnnotation11
    public String methodNotSingleLineGood() {
        return "";
    }

    @MyAnnotation15(value = "") public void method7() {};
}

@interface MyAnnotation11 {
}

@interface MyAnnotation12 {
}

@interface MyAnnotation13 {
}

@interface MyAnnotation14 {
}

@interface MyAnnotation15 {
    String value();
}
