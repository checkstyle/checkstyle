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

    @MyAnnotation11
    // violation below, 'Annotation 'MyAnnotation12' must be alone on a line, or all on one line.'
    @MyAnnotation12 @MyAnnotation13
    // violation above, 'Annotation 'MyAnnotation13' must be alone on a line, or all on one line.'
    @MyAnnotation14
    // violation above, 'Annotation 'MyAnnotation14' must be alone on a line, or all on one line.'
    void method3() {
    }

}

@MyAnnotation11
// violation below, 'Annotation 'MyAnnotation12' must be alone on a line, or all on one line.'
@MyAnnotation12 @MyAnnotation13
// violation above, 'Annotation 'MyAnnotation13' must be alone on a line, or all on one line.'
class TestClass {

    // violation below, 'Annotation 'MyAnnotation11' must be on a separate line from target.'
    @MyAnnotation11 int a;
}

@interface MyAnnotation11 {
}

@interface MyAnnotation12 {
}

@interface MyAnnotation13 {
}

@interface MyAnnotation14 {
}
