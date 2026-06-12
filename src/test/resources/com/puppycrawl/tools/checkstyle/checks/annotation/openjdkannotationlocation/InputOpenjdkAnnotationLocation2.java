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

    // 2 violations 4 lines below:
    //   'Annotation 'MyAnnotation12' must be alone on a line, or all on one line.'
    //   'Annotation 'MyAnnotation13' must be alone on a line, or all on one line.'
    @MyAnnotation11
    @MyAnnotation12 @MyAnnotation13
    @MyAnnotation14
    // violation above, 'Annotation 'MyAnnotation14' must be alone on a line, or all on one line.'
    void method4() {
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


// 2 violations 4 lines below:
    //   'Annotation 'MyAnnotation12' must be alone on a line, or all on one line.'
    //   'Annotation 'MyAnnotation13' must be alone on a line, or all on one line.'
@MyAnnotation11
@MyAnnotation12 @MyAnnotation13
class TestClassBad {

    @MyAnnotation11 @MyAnnotation12
    @MyAnnotation13 int a;
    // violation above 'Annotation 'MyAnnotation13' must be alone on a line, or all on one line.'

    @MyAnnotation11 @MyAnnotation12 @MyAnnotation13 int b;

    @MyAnnotation11 @MyAnnotation12 @MyAnnotation13
    int c;

    @MyAnnotation11
    @MyAnnotation12
    @MyAnnotation13
    int d;

    // violation below 'Annotation 'MyAnnotation11' must be on a separate line from target.'
    @MyAnnotation11 public int
            e = 10;

    // violation below, 'Annotation 'MyAnnotation11' must be on a separate line from target.'
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
