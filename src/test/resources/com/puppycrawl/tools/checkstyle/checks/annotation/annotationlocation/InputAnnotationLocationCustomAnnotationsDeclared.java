package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

/* Config:
 *
 * allowSamelineSingleParameterlessAnnotation = true
 */

@MyAnnotation11 @MyAnnotation12 @MyAnnotation13 // violation
public class InputAnnotationLocationCustomAnnotationsDeclared {

    @MyAnnotation13 // ok
    void method() {

    }

    @MyAnnotation13 // ok
    @MyAnnotation12 // ok
    void method2() {

    }

}

@interface MyAnnotation11 {
}

@interface MyAnnotation12 {
}

@interface MyAnnotation13 {
}
