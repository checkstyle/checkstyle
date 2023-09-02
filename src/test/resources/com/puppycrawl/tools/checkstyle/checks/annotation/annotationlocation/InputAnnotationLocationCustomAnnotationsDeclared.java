/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = false
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

@MyAnnotation11 @MyAnnotation12 @MyAnnotation13 // 3 violations
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
